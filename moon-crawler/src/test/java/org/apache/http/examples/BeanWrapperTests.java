package org.apache.http.examples;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/16
 */
public class BeanWrapperTests {

    public static void main(String[] args) {
        try {
            Object o = Class.forName("org.moonframework.crawler.storage.Node").newInstance();
            // Node node = new Node();

            //
            Map<String, Object> map = new HashMap<>();
            map.put("className", "com.budee.app");
            BeanWrapper beanWrapper = new BeanWrapperImpl(o);
            beanWrapper.setPropertyValues(map);

            System.out.println(o);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
