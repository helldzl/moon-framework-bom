package org.moonframework.web.autoconfigure;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.moonframework.security.core.AuthorizationService;
import org.moonframework.security.realm.StatelessRealm;
import org.moonframework.web.filter.StatelessAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Load on demand</p>
 * <p>http://shiro.apache.org/spring.html</p>
 * <p>You don't really normally need DelegatingFilterProxy in a Spring Boot application. If the ShiroFilterFactoryBean creates a Filter then it should be applied to all requests by default. </p>
 * <p>http://stackoverflow.com/questions/25241801/how-to-configure-shiro-with-spring-boot</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/1/12
 */
@Configuration
@ConditionalOnBean(AuthorizationService.class)
@ConditionalOnProperty(prefix = WebSecurityProperties.PREFIX, name = "appKey")
@EnableConfigurationProperties(WebSecurityProperties.class)
public class WebSecurityAutoConfiguration {

    @Autowired
    private AuthorizationService authorizationService;

    @Configuration
    @ConditionalOnMissingBean(AbstractShiroFilter.class)
    protected static class ShiroFilterConfiguration {

        @Autowired
        private WebSecurityProperties properties;

        @Autowired
        private DefaultSecurityManager defaultSecurityManager;

        @Bean
        public AbstractShiroFilter shiroFilter() throws Exception {
            // filter
            StatelessAuthenticationFilter authenticationFilter = new StatelessAuthenticationFilter();
            authenticationFilter.setAppKey(properties.getAppKey());
            authenticationFilter.setAppMap(properties.getKeys());

            Map<String, Filter> filters = new HashMap<>();
            filters.put("authenticationFilter", authenticationFilter);

            // factory bean
            ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
            factoryBean.setSecurityManager(defaultSecurityManager);
            factoryBean.setFilters(filters);
            factoryBean.setFilterChainDefinitions("/**=authenticationFilter");
            return (AbstractShiroFilter) factoryBean.getObject();
        }

    }

    @Configuration
    @ConditionalOnMissingBean(DefaultSecurityManager.class)
    protected static class SecurityManagerConfiguration {

        @Autowired
        @Qualifier("statelessRealm")
        private Realm statelessRealm;

        /**
         * <p>安全管理器</p>
         *
         * @return DefaultSecurityManager
         */
        @Bean
        public DefaultSecurityManager defaultSecurityManager() {
            DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(statelessRealm);
            securityManager.setCacheManager(new EhCacheManager());
            return securityManager;
        }

    }

    @Bean
    @ConditionalOnMissingBean(name = "statelessRealm")
    protected Realm statelessRealm() {
        StatelessRealm realm = new StatelessRealm();
        realm.setService(authorizationService);
        return realm;
    }

    /**
     * <p><a href="http://shiro.apache.org/spring.html">Integrating Apache Shiro into Spring-based Applications</a></p>
     */
    @Configuration
    @ConditionalOnMissingBean(LifecycleBeanPostProcessor.class)
    protected static class ShiroBeanPostProcessor {

        @Bean
        @Order(value = Ordered.LOWEST_PRECEDENCE)
        public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
            return new LifecycleBeanPostProcessor();
        }

    }

}
