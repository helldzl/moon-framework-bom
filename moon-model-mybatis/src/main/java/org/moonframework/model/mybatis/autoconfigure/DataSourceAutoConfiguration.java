package org.moonframework.model.mybatis.autoconfigure;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * <p>公用的数据源配置</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/1/13
 */
@Configuration
public class DataSourceAutoConfiguration {

    @Configuration
    // @MapperScan(basePackages = "com.xxx.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
    protected static class PrimaryDataSource {

        @Autowired
        @Qualifier("dataSource")
        private DataSource dataSource;

//        @Bean
//        @Primary
//        @ConfigurationProperties(prefix = "spring.datasource")
//        @Description("Primary DataSource")
//        public DataSource primaryDataSource() {
//            return DataSourceBuilder.create().build();
//        }

        @Bean(name = "sqlSessionFactory")
        @Description("A FactoryBean")
        public SqlSessionFactory sqlSessionFactory() throws Exception {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource);
            sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
            return sessionFactory.getObject();
        }

        @Bean(name = "transactionManager")
        @Description("DataSource transaction manager")
        public DataSourceTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean(name = "sqlSession")
        @Primary
        @Description("Sql session template")
        public SqlSessionTemplate sqlSessionTemplate() throws Exception {
            return new SqlSessionTemplate(sqlSessionFactory());
        }

        @Bean(name = "batchSqlSession")
        @Description("Sql session template for batch")
        public SqlSessionTemplate batchSqlSessionTemplate() throws Exception {
            return new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH);
        }

    }

    // TODO Secondary DataSource

//    @Configuration
////    @MapperScan(basePackages = "com.xxx.mapper", sqlSessionFactoryRef = "secondarySqlSessionFactory")
//    protected static class SecondaryDataSource {
//
//        @Bean(name = "dataSource2")
//        public DataSource dataSource() {
//            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
//            dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
//            dataSource.setUrl("jdbc:mysql://localhost:3306/qzl");
//            dataSource.setUsername("root");
//            dataSource.setPassword("root");
//            return dataSource;
//        }
//
//        @Bean(name = "secondaryDataSource")
//        @ConfigurationProperties(prefix = "spring.secondary")
//        @Description("Secondary DataSource")
//        public DataSource secondaryDataSource() {
//            return DataSourceBuilder.create().build();
//        }
//
//        @Bean(name = "secondarySqlSessionFactory")
//        @Description("A FactoryBean")
//        public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
//            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//            sessionFactory.setDataSource(secondaryDataSource());
//            return sessionFactory;
//        }
//
//        @Bean(name = "secondaryTransactionManager")
//        @Description("DataSource transaction manager")
//        public DataSourceTransactionManager transactionManager() {
//            return new DataSourceTransactionManager(secondaryDataSource());
//        }
//
//    }

}
