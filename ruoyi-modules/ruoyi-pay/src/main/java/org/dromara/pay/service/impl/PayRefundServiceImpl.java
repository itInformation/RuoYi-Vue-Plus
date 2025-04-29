package org.dromara.pay.service.impl;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.pay.domain.PayRefund;
import org.dromara.pay.domain.bo.PayRefundBo;
import org.dromara.pay.domain.bo.RefundBo;
import org.dromara.pay.domain.vo.PayOrderVo;
import org.dromara.pay.domain.vo.PayRefundVo;
import org.dromara.pay.enums.PayChannelEnum;
import org.dromara.pay.enums.RefundStatusEnum;
import org.dromara.pay.factory.RefundStrategyFactory;
import org.dromara.pay.mapper.PayRefundMapper;
import org.dromara.pay.service.IPayOrderService;
import org.dromara.pay.service.IPayRefundService;
import org.dromara.pay.service.IPayRefundStrategy;
import org.dromara.pay.utils.OrderNoGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    private final IPayOrderService payOrderService;
    private final RefundStrategyFactory refundStrategyFactory;
    private final OrderNoGenerator orderNoGenerator;
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
        lqw.eq(bo.getCreateBy() != null, PayRefund::getCreateBy, bo.getCreateBy());
        lqw.eq(bo.getCreateTime() != null, PayRefund::getCreateTime, bo.getCreateTime());
        lqw.eq(bo.getUpdateBy() != null, PayRefund::getUpdateBy, bo.getUpdateBy());
        lqw.eq(bo.getUpdateTime() != null, PayRefund::getUpdateTime, bo.getUpdateTime());
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
        // 2. 调用策略生成退款订单
        IPayRefundStrategy strategy = refundStrategyFactory.getStrategy(orderVo.getChannel());

        Object refundResponse = strategy.refund(payRefundBo);
        if (PayChannelEnum.ALIPAY.getCode().equals(orderVo.getChannel())){
            handleAlipayRefundResult(payRefundBo,(AlipayTradeRefundResponse)refundResponse);
        }
        return payRefundBo.getRefundNo();
    }

    /**
     * 支付宝退款结果处理
     */
    private void handleAlipayRefundResult(PayRefundBo refundBo, AlipayTradeRefundResponse response) {
        log.info("支付宝退款结果：{}", JsonUtils.toJsonString(response));
        if ("Y".equals(response.getFundChange())) {
            //这里处理全部退款的情况
            // 获取退款金额
            BigDecimal refundAmount = new BigDecimal(response.getRefundFee());
            if (refundBo.getOrderAmount().compareTo(refundAmount) == 0){
                log.info("处理全部退款的后续逻辑");
                updateRefundStatus(refundBo.getRefundId(), RefundStatusEnum.ALL_REFUND.getCode());
            }else{
                updateRefundStatus(refundBo.getRefundId(), RefundStatusEnum.PART_REFUND.getCode());
            }
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
    public void updateRefundStatus(Long refundId, String status) {
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
        refund.setRefundNo(orderNoGenerator.generate());
        refund.setOrderNo(orderVo.getOrderNo());
        refund.setTradeNo(orderVo.getTradeNo());
        refund.setOrderAmount(orderVo.getAmount());
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



}
