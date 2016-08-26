package org.moonframework.jsch.autoconfigure;

import org.moonframework.jsch.JschFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/21
 */
@Configuration
@ConditionalOnMissingBean(JschFactoryBean.class)
@ConditionalOnProperty(prefix = JschProperties.PREFIX, name = "host")
@EnableConfigurationProperties(JschProperties.class)
public class JschAutoConfiguration {

    @Autowired
    private JschProperties properties;

    @Bean
    public JschFactoryBean JschFactoryBean() {
        JschFactoryBean jschFactoryBean = new JschFactoryBean();
        jschFactoryBean.setHost(properties.getHost());
        jschFactoryBean.setPort(properties.getPort());
        jschFactoryBean.setUser(properties.getUser());
        jschFactoryBean.setPassword(properties.getPassword());
        jschFactoryBean.setKnownHosts(properties.getKnownHosts());
        jschFactoryBean.setStrictHostKeyChecking(properties.isStrictHostKeyChecking());
        return jschFactoryBean;
    }

}
