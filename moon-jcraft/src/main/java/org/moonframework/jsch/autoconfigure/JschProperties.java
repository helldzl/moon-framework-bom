package org.moonframework.jsch.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/21
 */
@ConfigurationProperties(prefix = JschProperties.PREFIX, ignoreUnknownFields = true)
public class JschProperties implements EnvironmentAware {

    public static final String PREFIX = "moon.jsch";

    private String host = "localhost";
    private int port = 22;
    private String user;
    private String password;

    private String knownHosts;
    private boolean StrictHostKeyChecking = true;

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKnownHosts() {
        return knownHosts;
    }

    public void setKnownHosts(String knownHosts) {
        this.knownHosts = knownHosts;
    }

    public boolean isStrictHostKeyChecking() {
        return StrictHostKeyChecking;
    }

    public void setStrictHostKeyChecking(boolean strictHostKeyChecking) {
        StrictHostKeyChecking = strictHostKeyChecking;
    }
}
