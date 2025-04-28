package org.dromara.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.pay.domain.bo.PayOrderBo;
import org.dromara.pay.domain.vo.PayConfigVo;
import org.dromara.pay.domain.vo.PayOrderVo;
import org.dromara.pay.service.IPayConfigService;
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
@RequiredArgsConstructor
@Component("alipay")
public class AliPayStrategy implements IPayStrategy {
    private final IPayConfigService payConfigService;
    private AlipayClient client;
    @PostConstruct
    public AlipayClient init() {
        PayConfigVo config = payConfigService.queryByChannel("alipay");
        if (config == null){
            throw new ServiceException("支付宝支付配置不存在");
        }
        AlipayConfig alipayConfig = new AlipayConfig();
//        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setAppId(config.getAppId());
        alipayConfig.setPrivateKey(config.getPrivateKey());
        alipayConfig.setFormat("json");
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setAlipayPublicKey(config.getPublicKey());
        alipayConfig.setSignType("RSA2");

        try {
            client = new DefaultAlipayClient(alipayConfig);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        return client;
    }
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
    public void processNotify(Map<String, String> params) {

    }

    @Override
    public PayOrderVo queryOrder(String orderNo) {
        return null;
    }
}
