package org.moonframework.intelligence;

import org.moonframework.intelligence.hash.CnHash;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/4/7
 */

public class CnHashTests {

    public static void main(String[] args) {
        try {
            long l = CnHash.hash("中文尽快睡觉EFI及积分叫哦我就佛教鸡尾酒覅未");
            // System.out.println("中文啊啊".hashCode());
            System.out.println(l);

            byte b = -127;
            System.out.println(b & 0xff);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
