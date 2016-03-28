package org.moon.mybatis.tool.dao;

import org.moon.mybatis.tool.entity.Column;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/8/17
 */
@Repository
public class GeneratorDao extends AppBaseDao {

    public List<Column> queryForList(String schema) {
        String sql = "SELECT `table_catalog`, `table_schema`, `table_name`, `column_name`, `ordinal_position`, `column_default`, `is_nullable`, `data_type`, `character_maximum_length`, `character_octet_length`, `numeric_precision`, `numeric_scale`, `character_set_name`, `collation_name`, `column_type`, `column_key`, `extra`, `privileges`, `column_comment` FROM `information_schema`.`columns` WHERE `TABLE_SCHEMA` = ?";
        return queryForList(Column.class, sql, schema);
    }

}
