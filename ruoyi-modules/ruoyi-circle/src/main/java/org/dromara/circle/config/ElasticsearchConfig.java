package org.dromara.circle.config;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:11 2025/5/2
 * modified by
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import java.time.Duration;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
            .connectedTo("115.227.18.54:9200")
            .withConnectTimeout(Duration.ofSeconds(30))
            .withSocketTimeout(Duration.ofSeconds(30))
            .build();
    }
}
