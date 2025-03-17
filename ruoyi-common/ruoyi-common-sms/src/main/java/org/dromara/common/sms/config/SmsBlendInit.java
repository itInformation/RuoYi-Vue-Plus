package org.dromara.common.sms.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.sms.enums.SupplierTypeEnum;
import org.dromara.sms4j.core.datainterface.SmsReadConfig;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 15:55
 */
@Component
@Slf4j
public class SmsBlendInit implements ApplicationRunner {
    private static final String prefix = "sms_config_";
    private Map<String, SmsReadConfig> smsReadConfigMap = new HashMap<>();
    @Autowired
    private ApplicationContext applicationContext;
    @PostConstruct
    public void init() throws BeansException {
        this.smsReadConfigMap = applicationContext.getBeansOfType(SmsReadConfig.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String smsType = prefix + SupplierTypeEnum.ALIYUN.getType();
        SmsReadConfig smsReadConfig = smsReadConfigMap.get(smsType);
        if (smsReadConfig == null){
            throw new RuntimeException("短信配置初始化失败，请检查配置");
        }
        SmsFactory.createSmsBlend(smsReadConfig);
        log.info("初始化短信配置成功，使用的是" + smsType );
    }
}
