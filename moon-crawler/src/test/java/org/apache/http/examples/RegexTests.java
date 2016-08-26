package org.apache.http.examples;

import org.junit.Test;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/27
 */
public class RegexTests {

    @Test
    public void testRegexp() {
        String s = "style=\"width: 58px; height: 73px; background-image: url(http://media.guitarcenter.com/is/image/MMGS7/H96926000001000_IMAGE_00?defaultImage=ics-trans&layer=comp&fit=constrain,1&wid=58&hei=73&fmt=png-alpha&op_sharpen=0&resMode=sharp2&op_usm=0.25,1,6,0); background-position: 50% 50%; background-repeat: no-repeat;\"";

        String r = ".*url([^)]).*";
        boolean b = s.matches(r);
        System.out.println(s.replaceAll("(.*url\\(|\\);.*)", ""));
        // assertEquals(true, b);
        //Pattern compile = Pattern.compile("");
        //compile.matcher(s);
    }

    @Test
    public void a() {
        String s = "/dd/ggg.jpd";
        boolean b = s.matches("^/[^/].*");
        System.out.println(b);

        s = "//www.baidu";
        b = s.matches("(?i)http.*|//.*");
        System.out.println(b);
    }

    public static void main(String[] args) {

    }
}
