package org.moonframework.web.utils;

import org.apache.shiro.codec.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/11/30
 */
public class SerializableUtils {

    /**
     * @param session session
     * @return T
     */
    public static <T> String serialize(T session) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            try {
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(session);
                return Base64.encodeToString(bos.toByteArray());
            } finally {
                if (oos != null)
                    oos.close();
                if (bos != null)
                    bos.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("serialize session error!", e);
        }
    }

    /**
     * @param str string
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String str) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            try {
                bis = new ByteArrayInputStream(Base64.decode(str));
                ois = new ObjectInputStream(bis);
                return (T) ois.readObject();
            } finally {
                if (ois != null)
                    ois.close();
                if (bis != null)
                    bis.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error!", e);
        }
    }

}
