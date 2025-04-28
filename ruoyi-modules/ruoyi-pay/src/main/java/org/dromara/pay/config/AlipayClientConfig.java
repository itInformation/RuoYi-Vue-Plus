package org.dromara.pay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.pay.domain.vo.PayConfigVo;
import org.dromara.pay.service.IPayConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/17 18:16
 */
@RequiredArgsConstructor
@Component
public class AlipayClientConfig {
    private final IPayConfigService payConfigService;
    @Bean
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
           return new DefaultAlipayClient(alipayConfig);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
