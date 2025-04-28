package org.dromara.pay.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.lock.LockTemplate;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.pay.domain.bo.PayBo;
import org.dromara.pay.domain.bo.PayOrderBo;
import org.dromara.pay.domain.vo.PayConfigVo;
import org.dromara.pay.domain.vo.PayOrderVo;
import org.dromara.pay.enums.PayStatusEnum;
import org.dromara.pay.factory.PayStrategyFactory;
import org.dromara.pay.service.IPayConfigService;
import org.dromara.pay.service.IPayOrderService;
import org.dromara.pay.service.IPayService;
import org.dromara.pay.service.IPayStrategy;
import org.dromara.pay.utils.OrderNoGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 17:40
 */
@Slf4j
@Service
public class PayServiceImpl implements IPayService {
    @Resource
    private PayStrategyFactory strategyFactory;
    @Resource
    private IPayOrderService payOrderService;
    @Resource
    private IPayConfigService payConfigService;
    @Resource
    private LockTemplate lockTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> payOrder(PayBo orderBo) {
        // 1. 校验业务参数

        PayOrderVo orderVo = validateAmount(orderBo.getAmount(), orderBo.getOrderId());

        // 2. 调用策略生成支付订单
        IPayStrategy strategy = strategyFactory.getStrategy(orderVo.getChannel());

        return strategy.pay(orderVo);
    }

    /**
     * 订单支付前校验
     */
    private PayOrderVo validateAmount(BigDecimal requestAmount, String orderId) {
        PayOrderVo order = payOrderService.queryById(orderId);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }

        // 使用BigDecimal的compareTo方法进行精确比较
        if (order.getAmount().compareTo(requestAmount) != 0) {
            throw new ServiceException("支付金额不一致");
        }
        return order;
    }


    @Transactional
    @Override
    public void processNotify(HttpServletRequest request) {
        Map<String, String> params = convertRequestParams(request);
        log.info("[支付宝回调] 收到通知参数：{}", params);
        // 1. 基础验证
//        if (!verifyBasicParams(params)) {
//            throw new ServiceException("参数验证失败");
//        }
//        // 2. 验证签名
//        if (!verifySignature(params)) {
//            log.error("[支付宝回调] 签名验证失败");
//            throw new ServiceException("签名验证失败");
//        }
//        // 3. 获取订单号
//        String orderNo = params.get("out_trade_no");
//        String tradeNo = params.get("trade_no");
//        BigDecimal amount = new BigDecimal(params.get("total_amount"));
//        // 1.使用Redis分布式锁
//        String lockKey = "pay:notify:lock:" + orderNo;
//        final LockInfo lockInfo = lockTemplate.lock(lockKey, 30000L, 5000L, RedissonLockExecutor.class);
//        if (null == lockInfo) {
//            log.warn("[支付宝回调] 正在处理交易：{}", tradeNo);
//            throw new RuntimeException("业务处理中,请稍后再试");
//        }
//        // 获取锁成功，处理业务
//        try {
//            // 2.查询订单
//            PayOrderVo order = payOrderService.queryById(orderNo);
//
//            // 3.检查订单状态
//            if (!"WAITING".equals(order.getStatus())) {
//                log.info("订单已处理，直接返回成功");
//                return;
//            }
//            // 2. 校验金额
//            if (order.getAmount().compareTo(amount) != 0) {
//                log.error("[支付宝回调] 金额不一致，订单：{}，通知：{}",
//                    order.getAmount(), amount);
//                throw new ServiceException("金额不一致");
//            }
//            // 4.更新订单状态
//            updateOrderStatus(orderNo, PayStatusEnum.SUCCESS.getCode());
//
//            // 5.记录处理日志
////            insertNotifyLog(params);
//
//        } finally {
//            //释放锁
//            lockTemplate.releaseLock(lockInfo);
//        }
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

    /**
     * 更新订单状态
     */
    private void updateOrderStatus(String orderNo, String status) {
        PayOrderBo bo = new PayOrderBo();
        bo.setOrderNo(orderNo);
        bo.setStatus(status);
        payOrderService.updateByBo(bo);
    }
}
