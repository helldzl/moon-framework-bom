package org.moonframework.crawler.autoconfigure;

import org.elasticsearch.client.Client;
import org.moonframework.crawler.elasticsearch.TransportClientFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/6
 */
@Configuration
@ConditionalOnMissingBean(TransportClientFactoryBean.class)
@ConditionalOnProperty(prefix = ElasticsearchProperties.PREFIX, name = "host")
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticsearchAutoConfiguration {

    @Autowired
    private ElasticsearchProperties properties;

    @Bean
    public Client transportClient() throws Exception {
        TransportClientFactoryBean transportClientFactoryBean = new TransportClientFactoryBean();
        transportClientFactoryBean.setHost(properties.getHost());
        transportClientFactoryBean.setPort(properties.getPort());
        transportClientFactoryBean.setClusterName(properties.getClusterName());
        transportClientFactoryBean.setTimeout(properties.getTimeout());
        return transportClientFactoryBean.getObject();
    }

}
