package org.moon.mybatis.tool;

import org.moon.mybatis.tool.entity.Column;
import org.moon.mybatis.tool.entity.Table;
import org.moon.mybatis.tool.service.GeneratorService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/8/17
 */
public class ExecuteMe {

    private final ApplicationContext context =
            new ClassPathXmlApplicationContext("spring-config.xml");

    public void all() {
        GeneratorService service = context.getBean(GeneratorService.class);
        service.process(service.findTables());
    }

    public void findTable(String schema) {
        GeneratorService service = context.getBean(GeneratorService.class);
        List<Table> list = service.findTables(schema);
        for (Table table : list) {
            System.out.println(table.getTableName());
            for (Column column : table.getColumns()) {
                System.out.println("    " + column.getColumnName());
            }
        }
    }

    public static void main(String[] args) {
        ExecuteMe connection = new ExecuteMe();
        connection.all();
        // connection.findTable("jd_data_wallet");

        System.out.println(System.getProperty("user.name"));
//        Enumeration<?> a = System.getProperties().propertyNames();
//        while (a.hasMoreElements()) {
//            System.out.println(a.nextElement());
//        }

    }

}
