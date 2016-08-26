package org.moonframework.jsch;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.FactoryBean;

import java.io.ByteArrayInputStream;
import java.util.Properties;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/21
 */
public class JschFactoryBean implements FactoryBean<Session> {

    private String host;
    private int port;
    private String user;
    private String password;

    private String knownHosts;
    private boolean StrictHostKeyChecking = true;

    @Override
    public Session getObject() throws Exception {
        JSch jsch = new JSch();
        if (knownHosts != null) {
            jsch.setKnownHosts(new ByteArrayInputStream(knownHosts.getBytes()));
        }
        Session session = jsch.getSession(user, host, port);
        session.setPassword(password);

        Properties config = new Properties();
        // It must not be recommended, but if you want to skip host-key check, set 'no'
        config.put("StrictHostKeyChecking", StrictHostKeyChecking ? "yes" : "no");
        session.setConfig(config);
        return session;
    }

    @Override
    public Class<?> getObjectType() {
        return Session.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    // get and set method

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
