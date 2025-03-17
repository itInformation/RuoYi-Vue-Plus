package org.dromara.pay.config;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import org.dromara.pay.domain.vo.PayConfigVo;
import org.dromara.pay.service.IPayConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/17 18:23
 */
// com.ruoyi.common.config.WechatPayGlobalConfig.java
@Configuration
public class WechatPayGlobalConfig {

    @Autowired
    private IPayConfigService payConfigService;

//    @Bean
    public Config wxPayConfig() throws IOException {
        PayConfigVo configVo = payConfigService.queryByChannel("wechat");
        if (configVo == null){
            throw new RuntimeException("微信支付配置不存在");
        }
        return new RSAAutoCertificateConfig.Builder()
            .merchantId(configVo.getMchId())
            .privateKeyFromPath(configVo.getPrivateKey())
            .merchantSerialNumber("")
            .apiV3Key("")
            .build();
    }

//    @Bean
    public NativePayService nativePayService() throws IOException {
        return new NativePayService.Builder()
            .config(wxPayConfig())
            .build();
    }
}
