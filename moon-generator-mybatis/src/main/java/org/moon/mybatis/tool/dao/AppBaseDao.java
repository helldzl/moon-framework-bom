package org.moon.mybatis.tool.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/8/17
 */
public class AppBaseDao extends JdbcDaoSupport {

    /**
     *
     */
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    public void initDataSource(DataSource dataSource) {
        setDataSource(dataSource);
    }

    /**
     * queryForList
     *
     * @param clazz clazz
     * @param sql   sql
     * @param args  args
     * @param <T>   T
     * @return list
     */
    public <T> List<T> queryForList(Class<T> clazz, String sql, Object... args) {
        try {
            List<T> list = getJdbcTemplate().query(sql, args, BeanPropertyRowMapper.newInstance(clazz));
            logger.info(sql);
            return list;
        } catch (Exception e) {
            logger.error("queryForList error!", e);
            throw new RuntimeException();
        }
    }

}
