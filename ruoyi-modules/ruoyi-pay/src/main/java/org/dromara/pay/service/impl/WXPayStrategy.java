package org.dromara.pay.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import org.dromara.pay.domain.PayConfig;
import org.dromara.pay.domain.PayOrder;
import org.dromara.pay.service.IPayStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 16:37
 */
@Component("wxPayStrategy")
public class WXPayStrategy implements IPayStrategy {
    private final AlipayClient client;

    public WXPayStrategy(PayConfig config) {
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
        return Map.of();
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
