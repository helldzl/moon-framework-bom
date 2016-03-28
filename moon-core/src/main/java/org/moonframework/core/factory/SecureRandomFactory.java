package org.moonframework.core.factory;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/23
 */
public final class SecureRandomFactory {

    private static SecureRandom secureRandom;

    static {
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public static SecureRandom getInstance() {
        return secureRandom;
    }

    public static long nextLong() {
        return secureRandom.nextLong();
    }

    public static int nextInt() {
        return secureRandom.nextInt();
    }

}
