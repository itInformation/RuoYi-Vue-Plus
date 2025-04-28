package org.dromara.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.executor.RedissonLockExecutor;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.pay.domain.bo.RefundBo;
import org.dromara.pay.domain.vo.PayConfigVo;
import org.dromara.pay.domain.vo.PayOrderVo;
import org.dromara.pay.domain.vo.RefundResult;
import org.dromara.pay.enums.PayChannelEnum;
import org.dromara.pay.enums.RefundStatusEnum;
import org.dromara.pay.factory.RefundStrategyFactory;
import org.dromara.pay.service.*;
import org.dromara.pay.utils.OrderNoGenerator;
import org.springframework.stereotype.Service;
import org.dromara.pay.domain.bo.PayRefundBo;
import org.dromara.pay.domain.vo.PayRefundVo;
import org.dromara.pay.domain.PayRefund;
import org.dromara.pay.mapper.PayRefundMapper;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 退款记录Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-28
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class PayRefundServiceImpl implements IPayRefundService {

    private final PayRefundMapper baseMapper;
    private AlipayClient alipayClient;
    private IPayOrderService payOrderService;
    private RefundStrategyFactory refundStrategyFactory;
    private IPayConfigService payConfigService;
    @Resource
    private LockTemplate lockTemplate;
    /**
     * 查询退款记录
     *
     * @param refundId 主键
     * @return 退款记录
     */
    @Override
    public PayRefundVo queryById(Long refundId){
        return baseMapper.selectVoById(refundId);
    }

    /**
     * 分页查询退款记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 退款记录分页列表
     */
    @Override
    public TableDataInfo<PayRefundVo> queryPageList(PayRefundBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<PayRefund> lqw = buildQueryWrapper(bo);
        Page<PayRefundVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的退款记录列表
     *
     * @param bo 查询条件
     * @return 退款记录列表
     */
    @Override
    public List<PayRefundVo> queryList(PayRefundBo bo) {
        LambdaQueryWrapper<PayRefund> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<PayRefund> buildQueryWrapper(PayRefundBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<PayRefund> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(PayRefund::getRefundId);
        lqw.eq(StringUtils.isNotBlank(bo.getRefundNo()), PayRefund::getRefundNo, bo.getRefundNo());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderNo()), PayRefund::getOrderNo, bo.getOrderNo());
        lqw.eq(StringUtils.isNotBlank(bo.getTradeNo()), PayRefund::getTradeNo, bo.getTradeNo());
        lqw.eq(bo.getAmount() != null, PayRefund::getAmount, bo.getAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getReason()), PayRefund::getReason, bo.getReason());
        lqw.eq(bo.getFinishTime() != null, PayRefund::getFinishTime, bo.getFinishTime());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), PayRefund::getStatus, bo.getStatus());
        lqw.eq(bo.getCreatedBy() != null, PayRefund::getCreatedBy, bo.getCreatedBy());
        lqw.eq(bo.getCreatedTime() != null, PayRefund::getCreatedTime, bo.getCreatedTime());
        lqw.eq(bo.getUpdatedBy() != null, PayRefund::getUpdatedBy, bo.getUpdatedBy());
        lqw.eq(bo.getUpdatedTime() != null, PayRefund::getUpdatedTime, bo.getUpdatedTime());
        return lqw;
    }

    /**
     * 新增退款记录
     *
     * @param bo 退款记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(PayRefundBo bo) {
        PayRefund add = MapstructUtils.convert(bo, PayRefund.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRefundId(add.getRefundId());
        }
        return flag;
    }

    /**
     * 修改退款记录
     *
     * @param bo 退款记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(PayRefundBo bo) {
        PayRefund update = MapstructUtils.convert(bo, PayRefund.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(PayRefund entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除退款记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    @Transactional
    public String refund(RefundBo refundBo) {
        // 1. 参数校验
        PayOrderVo orderVo = validateRefund(refundBo);

        PayRefundBo payRefundBo = buildRefundBo(refundBo, orderVo);
        insertByBo(payRefundBo);
        // 2. 调用策略生成支付订单
        IPayRefundStrategy strategy = refundStrategyFactory.getStrategy(orderVo.getChannel());

        Object refund = strategy.refund(payRefundBo);
        if (PayChannelEnum.ALIPAY.getCode().equals(orderVo.getChannel())){
            handleRefundResult(payRefundBo,(AlipayTradeRefundResponse)refund);
        }


        return payRefundBo.getRefundNo();
    }
    private void handleRefundResult(PayRefundBo refundBo, AlipayTradeRefundResponse response) {
        if ("Y".equals(response.getFundChange())) {
            updateRefundStatus(refundBo.getRefundId(), RefundStatusEnum.SUCCESS.getCode());
            // 更新原订单已退金额
            payOrderService.updateRefundAmount(
                refundBo.getOrderNo(),
                refundBo.getAmount()
            );
        } else {
            updateRefundStatus(refundBo.getRefundId(), RefundStatusEnum.FAILURE.getCode());
        }
    }

    /**
     * 处理订单状态
     */
    private void updateRefundStatus(Long refundId, String status) {
        PayRefundBo refundBo = new PayRefundBo();
        refundBo.setRefundId(refundId);
        refundBo.setStatus(status);
        refundBo.setFinishTime(LocalDateTime.now());
        updateByBo(refundBo);
    }

    /**
     * 构建退款记录Bo
     */
    private PayRefundBo buildRefundBo(RefundBo refundBo, PayOrderVo orderVo) {
        PayRefundBo refund = new PayRefundBo();
        refund.setRefundNo(OrderNoGenerator.generate());
        refund.setOrderNo(orderVo.getOrderId());
        refund.setTradeNo(orderVo.getTradeNo());
        refund.setAmount(refundBo.getRefundAmount());
        refund.setReason(refundBo.getReason());
        refund.setStatus(RefundStatusEnum.PROCESSING.getCode());
        return refund;
    }

    private PayOrderVo validateRefund(RefundBo refundBo) {
        // 1. 查询原订单
        PayOrderVo order = payOrderService.queryById(refundBo.getOrderId());
        if (order == null) {
            throw new ServiceException("订单不存在");
        }

        if (order.getRefundAmount().add(refundBo.getRefundAmount()).compareTo(order.getAmount()) > 0) {
            throw new ServiceException("超出可退金额");
        }
        return order;
    }

    @Override
    public void processNotify(HttpServletRequest request){
        Map<String, String> params = convertRequestParams(request);
        // 1. 基础验证
        if (!verifyBasicParams(params)) {
            throw new ServiceException("参数验证失败");
        }
        // 2. 验证签名
        if (!verifySignature(params)) {
            log.error("[支付宝回调] 签名验证失败");
            throw new ServiceException("签名验证失败");
        }
        // 3. 获取订单号
        String orderNo = params.get("out_trade_no");
        String tradeNo = params.get("trade_no");
        BigDecimal amount = new BigDecimal(params.get("total_amount"));
        // 1.使用Redis分布式锁
        String lockKey = "pay:notify:lock:" + orderNo;
        final LockInfo lockInfo = lockTemplate.lock(lockKey, 30000L, 5000L, RedissonLockExecutor.class);
        if (null == lockInfo) {
            log.warn("[支付宝回调] 正在处理交易：{}", tradeNo);
            throw new RuntimeException("业务处理中,请稍后再试");
        }
        // 获取锁成功，处理业务
        try {
            // 2.查询订单
            PayOrderVo order = payOrderService.queryById(orderNo);

            // 3.检查订单状态
            if (!"WAITING".equals(order.getStatus())) {
                log.info("订单已处理，直接返回成功");
                return;
            }
            // 2. 校验金额
            if (order.getAmount().compareTo(amount) != 0) {
                log.error("[支付宝回调] 金额不一致，订单：{}，通知：{}",
                    order.getAmount(), amount);
                throw new ServiceException("金额不一致");
            }
            // 4.更新订单状态
            //todo 退款逻辑待完善
            updateRefundStatus(null, RefundStatusEnum.SUCCESS.getCode());

            // 5.记录处理日志
//            insertNotifyLog(params);

        } finally {
            //释放锁
            lockTemplate.releaseLock(lockInfo);
        }
    }


    private Map<String, String> convertRequestParams(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> String.join(",", entry.getValue())
            ));
    }

    // 基础参数校验
    private boolean verifyBasicParams(Map<String, String> params) {
        return params.containsKey("trade_status")
            && params.containsKey("out_trade_no")
            && params.containsKey("total_amount");
    }

    // 签名验证
    private boolean verifySignature(Map<String, String> params) {
        try {
            PayConfigVo config = payConfigService.queryByChannel("alipay");
            return AlipaySignature.rsaCheckV1(
                params,
                config.getPublicKey(),
                "UTF-8", "RSA2"

            );
        } catch (AlipayApiException e) {
            log.error("[支付宝回调] 验签异常", e);
            return false;
        }
    }
}
