package org.moonframework.crawler.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/6
 */
@ConfigurationProperties(prefix = ElasticsearchProperties.PREFIX, ignoreUnknownFields = true)
public class ElasticsearchProperties implements EnvironmentAware {

    public static final String PREFIX = "moon.crawler.es";

    private String clusterName = "elasticsearch";
    private String host = "localhost";
    private int port = 9300;
    private boolean sniff = true;
    private int timeout = 5;

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
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

    public boolean isSniff() {
        return sniff;
    }

    public void setSniff(boolean sniff) {
        this.sniff = sniff;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
