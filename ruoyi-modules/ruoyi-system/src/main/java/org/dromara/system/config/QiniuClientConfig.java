package org.dromara.system.config;

import com.qiniu.http.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/31 17:15
 */
@Component
public class QiniuClientConfig {
    @Bean
    public Client qiniuClient(){

       return new Client();
    }
}
