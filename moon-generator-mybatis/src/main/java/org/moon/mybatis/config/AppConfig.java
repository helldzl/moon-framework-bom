package org.moon.mybatis.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/8/17
 */
@Configuration
@PropertySource("jdbc.properties")
public class AppConfig {

    @Resource
    Environment env;

    @Bean(name = "dataSource")
    @Description("Provides a basic data source of a bean")
    public DataSource dataSource() throws PropertyVetoException {
        // instantiate, configure and return DataSource bean...
        // ComboPooledDataSource dataSource = new ComboPooledDataSource();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        return dataSource;
    }

}
