package org.dromara.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.pay.domain.PayConfig;
import org.dromara.pay.domain.PayOrder;
import org.dromara.pay.service.IPayStrategy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 16:37
 */
@Component("aliPayStrategy")
public class AliPayStrategy implements IPayStrategy {
    private final AlipayClient client;

    public AliPayStrategy(PayConfig config) {
        this.client = new DefaultAlipayClient(
            "https://openapi.alipay.com/gateway.do",
            config.getAppId(),
            config.getMchId(),
            "json",
            "UTF-8",
            config.getApiKey(),
            "RSA2");
    }
    @Override
    public Map<String, String> createOrder(PayOrder order) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(null);
        request.setNotifyUrl(order.getNotifyUrl());

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(order.getOrderNo());
        model.setTotalAmount(order.getAmount().toString());
        model.setSubject(order.getSubject());
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
    public void processNotify(Map<String, String> params) {

    }

    @Override
    public PayOrder queryOrder(String orderNo) {
        return null;
    }
}
