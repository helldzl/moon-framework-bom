package org.moonframework.amqp.rabbit.support;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author quzile
 */
public class Conversion {

    public static Object byteArrayToObject(byte[] buffer) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer));
        Object ob = ois.readObject();
        ois.close();
        return ob;
    }

    public static byte[] objectToByteArray(Object obj) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        byte[] bytes = baos.toByteArray();
        baos.close();
        return bytes;
    }

}
