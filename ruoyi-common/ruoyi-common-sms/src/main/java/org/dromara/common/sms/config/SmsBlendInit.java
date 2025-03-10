package org.dromara.common.sms.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 15:55
 */
@Component
@Slf4j
public class SmsBlendInit  implements ApplicationRunner {
    @Autowired
    ReadConfig config;

//    @EventListener
//    public void init(ContextRefreshedEvent event){
//        // 创建SmsBlend 短信实例
//
//    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SmsFactory.createSmsBlend(config,"");
        log.info("初始化短信配置成功");
    }
}
