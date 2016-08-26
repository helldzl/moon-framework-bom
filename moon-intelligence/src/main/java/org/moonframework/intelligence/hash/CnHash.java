package org.moonframework.intelligence.hash;

import java.io.UnsupportedEncodingException;

/**
 * <p>中文字符串特征向量的散列算法</p>
 *
 * @author quzile
 * @version 1.0
 * @since 2016/4/7
 */
public class CnHash {

    // 最大中文编码
    private static final int MAX_CN_CODE = 6768;
    // 最大编码
    private static final int MAX_CODE = 6768 + 117;
    // 记录用多少位编码可以表示一个中文字符, 2的13次方 8192 > MAX_CN_CODE
    private static final int MAX_BIT = 13;

    /**
     * <p>根据输入返回SIM HASH</p>
     *
     * @param input 输入
     * @return SIM HASH
     * @throws Exception
     */
    public static long hash(String input) {
        try {
            if (input == null || "".equals(input))
                return -1;

            int maxBit = MAX_BIT;
            long simHash = 0;
            for (int i = 0, length = input.length(); i < length; i++) {
                simHash *= MAX_CODE;
                simHash += getHashCode(input.charAt(i));
                maxBit += MAX_BIT;
            }

            long value = simHash;
            for (int i = 0, length = (64 / maxBit); i <= length; i++) {
                simHash = simHash << maxBit;
                simHash += value;
            }
            return simHash;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param b byte
     * @return integer
     */
    private static int byte2int(byte b) {
        return b & 0xff;
    }

    /**
     * <p>取得中文字符的散列码</p>
     *
     * @param c char
     * @return integer
     * @throws UnsupportedEncodingException
     */
    private static int getHashCode(char c) throws UnsupportedEncodingException {
        byte[] b = String.valueOf(c).getBytes();
        switch (b.length) {
            case 1:
                return byte2int(b[0]) - 9 + MAX_CN_CODE;
            case 2:
                return (byte2int(b[0]) - 176) * 94 + (byte2int(b[1]) - 161);
            case 3:
                return (byte2int(b[0]) - 179) * 67 + (byte2int(b[1]) - 151) + (byte2int(b[2]) - 107);
        }
        return c;
    }

}
