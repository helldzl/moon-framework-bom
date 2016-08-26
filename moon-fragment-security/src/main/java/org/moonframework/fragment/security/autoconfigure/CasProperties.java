package org.moonframework.fragment.security.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/22
 */
@ConfigurationProperties(prefix = CasProperties.PREFIX, ignoreUnknownFields = true)
public class CasProperties implements EnvironmentAware {

    public static final String PREFIX = "moon.security.cas";

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    // TODO

}
