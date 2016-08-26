package org.moonframework.crawler.elasticsearch;

import org.carrot2.elasticsearch.ClusteringPlugin;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.FactoryBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/5
 */
public class TransportClientFactoryBean implements FactoryBean<Client> {

    private String host = "127.0.0.1";
    private String clusterName = "elasticsearch";
    private int port = 9300;
    private int timeout = 5;

    private Client client;

    @Override
    public Client getObject() throws Exception {
        if (client == null)
            client = createInstance();
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return Client.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    protected Client createInstance() throws UnknownHostException {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", true)
                .put("client.transport.ping_timeout", timeout, TimeUnit.SECONDS)
                .build();
        return TransportClient.builder()
                .settings(settings)
                .addPlugin(ClusteringPlugin.class)  // 添加 Carrot2 聚类插件
                .build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
    }

    // get and set method

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
