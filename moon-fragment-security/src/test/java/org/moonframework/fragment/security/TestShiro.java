package org.moonframework.fragment.security;

import org.apache.shiro.codec.Base64;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/21
 */
public class TestShiro {

    public static void main(String []args){
        String s = "cookie-security";
        String r = Base64.encodeToString(s.getBytes());
        System.out.println(r);

        byte[] b =  Base64.decode(r);
        System.out.println(new String(b));

    }
}
