package org.dromara.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.executor.RedissonLockExecutor;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.pay.domain.vo.PayConfigVo;
import org.dromara.pay.domain.vo.PayOrderVo;
import org.dromara.pay.enums.PayStatusEnum;
import org.dromara.pay.service.IPayConfigService;
import org.dromara.pay.service.IPayOrderService;
import org.dromara.pay.service.IPayStrategy;
import org.dromara.system.service.ICreatorAssetService;
import org.dromara.system.service.IUserAssetService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 16:37
 */
@RequiredArgsConstructor
@Component("alipayStrategy")
@Slf4j
public class AliPayStrategy implements IPayStrategy {
    private final AlipayClient client;
    private final IPayConfigService payConfigService;
    private final LockTemplate lockTemplate;
    private final IPayOrderService payOrderService;
    private final IUserAssetService userAssetService;
    private final ICreatorAssetService creatorAssetService;

    @Override
    public Map<String, String> pay(PayOrderVo orderVo) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(null);
        request.setNotifyUrl(orderVo.getNotifyUrl());

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(orderVo.getOrderNo());
        model.setTotalAmount(orderVo.getAmount().toString());
        model.setSubject(orderVo.getSubject());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);
        try {
            String form = client.pageExecute(request).getBody();
            return Collections.singletonMap("form", form);
        } catch (AlipayApiException e) {
            throw new ServiceException("支付宝下单失败");
        }

    }

    @Override
    public boolean verifyNotify(Map<String, String> params) {
        return false;
    }

    @Override
    @Transactional
    public void processNotify(Map<String, String> params) {
//        String str = "{\"gmt_create\":\"2025-04-29 14:08:35\",\"charset\":\"UTF-8\",\"gmt_payment\":\"2025-04-29 14:08:46\",\"notify_time\":\"2025-04-29 14:08:47\",\"subject\":\"bmw 1250 adv again 004\",\"sign\":\"aHp/pRtQeAQDxhD7vkD22YykhGsV9j7pLyfnrrE21shhUbyYXuF4SUCO9EcVN0NfpCv+cIXU54ZEfQishOvWVOptoLpK++DpkuXDgckF+2FqTvqoZ1ddkt2zIYUA6YHBZE0la7B0X3CndQdQrFDBItcwNL7JJsjKYMlz1KKOUjBUGTnYWSO2eixGwzYOozWZmXSIYKFOKam29Z8Xw+EBk6498vb134fsJr8KMgNAEVhCbZHagjsUoNLvwQLewYOq0f6itGlRkTfhFRU2vOSu01iSt0liFs2+suwjdnk4IyeOp8lyTj57PZgJBqRJXKpTAzmS/2Skr2gUmlK2ZmjUQA==\",\"buyer_id\":\"2088722036641399\",\"invoice_amount\":\"34.00\",\"version\":\"1.0\",\"notify_id\":\"2025042901222140847141390505835583\",\"fund_bill_list\":\"[{\\\"amount\\\":\\\"34.00\\\",\\\"fundChannel\\\":\\\"ALIPAYACCOUNT\\\"}]\",\"notify_type\":\"trade_status_sync\",\"out_trade_no\":\"25042914060501000001\",\"total_amount\":\"34.00\",\"trade_status\":\"TRADE_SUCCESS\",\"trade_no\":\"2025042922001441390505666434\",\"auth_app_id\":\"9021000137664576\",\"receipt_amount\":\"34.00\",\"point_amount\":\"0.00\",\"buyer_pay_amount\":\"34.00\",\"app_id\":\"9021000137664576\",\"sign_type\":\"RSA2\",\"seller_id\":\"2088721036641381\"}";
//        Map<String,String> params = JsonUtils.parseObject(str,Map.class);
        log.info("[支付宝支付回调] 收到通知参数：{}", JsonUtils.toJsonString(params));
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
        String tradeSuccess = params.get("trade_status");
        String subject = params.get("subject");
        BigDecimal amount = new BigDecimal(params.get("total_amount"));
        // 1.使用Redis分布式锁
        String lockKey = "pay:notify:lock:" + orderNo;
        final LockInfo lockInfo = lockTemplate.lock(lockKey, 30000L, 5000L, RedissonLockExecutor.class);
        if (null == lockInfo) {
            log.warn("[支付宝回调] 正在处理交易：{}", orderNo);
            throw new RuntimeException("业务处理中,请稍后再试");
        }
        // 获取锁成功，处理业务
        try {
            // 2.查询订单
            PayOrderVo order = payOrderService.queryByOrderNo(orderNo);

            // 3.检查订单状态
            if (!"WAITING".equals(order.getStatus())) {
                log.info("订单已处理，直接返回成功");
                return;
            }
            if (!"TRADE_SUCCESS".equals(tradeSuccess)){
                log.info("支付宝回调trade_status没有成功，实际是{}",tradeSuccess);
            }
            // 2. 校验金额
            if (order.getAmount().compareTo(amount) != 0) {
                log.error("[支付宝回调] 金额不一致，订单：{}，通知：{}",
                    order.getAmount(), amount);
                throw new ServiceException("金额不一致");
            }
            // 4.更新订单状态
            payOrderService.updateOrderStatusAndTradeNo(order.getOrderId(), PayStatusEnum.SUCCESS.getCode(),tradeNo);
            userAssetService.consumeAmount(order.getUserId(),amount);
            creatorAssetService.addIncome(order.getUserId(),amount,subject);
            //todo 更新用户资产表  包括创作者和普通用户的资产表

            // 5.记录处理日志
//            insertNotifyLog(params);

        } finally {
            //释放锁
            lockTemplate.releaseLock(lockInfo);
        }
    }
    // 基础参数校验
    private boolean verifyBasicParams(Map<String, String> params) {
        return params.containsKey("trade_status")
            && params.containsKey("out_trade_no")
            && params.containsKey("total_amount")
            && params.containsKey("trade_no");
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


    @Override
    public PayOrderVo queryOrder(String orderNo) {
        return null;
    }
}
