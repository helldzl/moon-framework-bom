package org.moonframework.web.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/1/12
 */
@ConfigurationProperties(prefix = WebSecurityProperties.PREFIX, ignoreUnknownFields = true)
public class WebSecurityProperties implements EnvironmentAware {

    public static final String PREFIX = "moon.security.web";

    private Environment environment;

    private String appKey;
    private String appSecret;

    private Map<String, String> keys = new HashMap<>();
    private Map<String, String> secrets = new HashMap<>();

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Map<String, String> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, String> keys) {
        this.keys = keys;
    }

    public Map<String, String> getSecrets() {
        return secrets;
    }

    public void setSecrets(Map<String, String> secrets) {
        this.secrets = secrets;
    }
}
