package org.moon.mybatis.tool.velocity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.util.Properties;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/8/17
 */
public class VelocityFactory {

    private static VelocityFactory factory = new VelocityFactory();
    private final Logger logger = LogManager.getLogger(this.getClass());

    public static VelocityFactory getInstance() {
        return factory;
    }

    private VelocityFactory() {
        InputStream is = null;
        try {
            try {
                is = getClass().getClassLoader().getResourceAsStream("velocity.properties");
                Properties prop = new Properties();
                prop.load(is);
                Velocity.init(prop);
                logger.info("Velocity initializing successful!");
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        } catch (Exception e) {
            logger.error("Problem initializing Velocity : " + e);
            throw new RuntimeException();
        }
    }

    /**
     * @param context
     * @param templateName
     * @param filename
     */
    public void generate(VelocityContext context, String templateName, String filename) {
        generate(context, templateName, new File(filename));
    }

    /**
     * @param context
     * @param templateName
     * @param file
     */
    public void generate(VelocityContext context, String templateName, File file) {
        try {
            Writer writer = null;
            try {
                file.getParentFile().mkdirs();
                writer = new FileWriter(file);
                Velocity.mergeTemplate(templateName, "UTF-8", context, writer);
                writer.flush();
                logger.info("Generate successful! filename : " + file.getName());
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        } catch (Exception e) {
            logger.error("Generate error! filename : " + file.getName(), e);
        }
    }

}
