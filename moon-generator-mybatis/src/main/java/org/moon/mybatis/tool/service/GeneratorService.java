package org.moon.mybatis.tool.service;

import org.apache.velocity.VelocityContext;
import org.moon.mybatis.tool.dao.GeneratorDao;
import org.moon.mybatis.tool.entity.Column;
import org.moon.mybatis.tool.entity.Table;
import org.moon.mybatis.tool.velocity.VelocityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/8/17
 */
@Service
public class GeneratorService {

    @Autowired
    private GeneratorDao generatorDao;

    @Value("${app.schema}")
    private String schema;

    @Value("${app.class.name}")
    private String baseClassName;

    @Value("${app.package.name}")
    private String packageName;

    @Value("${app.path}")
    private String path;

    @Value("${app.excludes}")
    private String excludes;

    private Random random = new Random();

    public List<Table> findTables() {
        return findTables(schema);
    }

    public List<Table> findTables(String schema) {
        List<Table> tables = new ArrayList<>();
        generatorDao.queryForList(schema)
                .stream()
                .collect(Collectors.groupingBy(Column::getTableName))
                .forEach((name, columns) -> tables.add(new Table(name, columns)));
        return tables;
    }

    public void process(List<Table> list) {
        for (Table table : list) {
            process(table, path, packageName);
            process(table, path);
        }
        process(list, path);
    }

    /**
     * 处理实体文件
     *
     * @param table
     * @param path
     * @param packageName
     */
    public void process(Table table, String path, String packageName) {
        VelocityFactory factory = VelocityFactory.getInstance();
        VelocityContext context = new VelocityContext();

        // filter and set imports
        List<Column> columns = filter(table, new HashSet<>(Arrays.asList(excludes.split(","))));
        table.setImports(columns);

        // user and date
        context.put("user", System.getProperty("user.name"));
        context.put("date", dateFormat());

        // table details
        context.put("table", table);
        context.put("packageName", packageName);
        context.put("columns", columns);

        // random
        context.put("serial", random.nextLong());
        if (baseClassName != null && !"".equals(baseClassName)) {
            context.put("baseClassName", baseClassName); // 使用全限定名称
            context.put("parentClass", baseClassName.substring(baseClassName.lastIndexOf(".") + 1));
        }

        //
        String filepath = filepath(path, packageName);
        File file = new File(new File(filepath), table.getClassName() + ".java");
        factory.generate(context, "/vm/entity.vm", file);
    }

    /**
     * 处理映射文件
     *
     * @param table
     * @param path
     */
    private void process(Table table, String path) {
        VelocityFactory factory = VelocityFactory.getInstance();
        VelocityContext context = new VelocityContext();

        context.put("table", table);
        context.put("namespace", packageName + "." + table.getClassName());

        File file = new File(path, table.getClassName() + "Mapper.xml");
        factory.generate(context, "/vm/mapper.vm", file);
    }

    /**
     * 处理配置文件
     *
     * @param list
     * @param path
     */
    private void process(List<Table> list, String path) {
        VelocityFactory factory = VelocityFactory.getInstance();
        VelocityContext context = new VelocityContext();

        context.put("tables", list);
        context.put("packageName", packageName);
        context.put("directory", packageName.replace('.', '/'));

        File file = new File(new File(path, "resources"), "mybatis-config.xml");
        factory.generate(context, "/vm/mybatis-config.vm", file);
    }

    private List<Column> filter(Table table, Set<String> set) {
        return table.getColumns().stream()
                .filter(column -> !set.contains(column.getColumnName().toLowerCase()))
                .collect(Collectors.toList());
    }

    private String dateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    private String filepath(String path, String packageName) {
        return path + File.separator + packageName.replace(".", File.separator);
    }

}
