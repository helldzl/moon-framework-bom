package org.moonframework.office;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;


/**
 * adapter pattern
 * <p>
 * <p>
 * 对office产品操作的抽象组件, 所有具体产品都应该扩展此类
 * </p>
 *
 * @author quzile
 */
@SuppressWarnings("unchecked")
public abstract class AbstractOfficeComponent<T> implements OfficeComponent<T> {

    protected final Logger logger = LogManager.getLogger(this.getClass());
    protected static final SimpleDateFormat DATE = new SimpleDateFormat(
            "yyyy-MM-dd");
    protected static final SimpleDateFormat DATETIME = new SimpleDateFormat(
            "yyyy-MM-dd mm:HH:ss");

    /**
     * 是否启动重写
     */
    private boolean rewrite;
    private Class<T> entityClass;

    {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) type;
        entityClass = (Class<T>) p.getActualTypeArguments()[0];
    }

    // constructor

    public AbstractOfficeComponent() {
    }

    public AbstractOfficeComponent(boolean rewrite) {
        this.rewrite = rewrite;
    }

    /**
     * @return
     */
    public T newEntity() {
        T t = null;
        try {
            t = entityClass.newInstance();
            // System.out.println(p);
            // System.out.println(entityClass);
            // System.out.println(t instanceof PublicItemsConfig);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return t;
    }

    // API

    @Override
    public void create(File file) {
        // TODO Auto-generated method stub

    }

    @Override
    public ResultEntity<T> readForList(File file) {
        // TODO Auto-generated method stub
        return null;
    }

    // get set method

    public boolean isRewrite() {
        return rewrite;
    }

    public void setRewrite(boolean rewrite) {
        this.rewrite = rewrite;
    }

}
