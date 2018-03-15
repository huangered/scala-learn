package com.yih.filter.secret;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecretKeyGenerator {
    private static volatile SecretKeyGenerator instance;
    private final String secret = "secret";
    private SecretKey key;

    public SecretKeyGenerator() {
        byte[] encodedKey = Base64.decodeBase64(secret);
        key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public static SecretKeyGenerator getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (SecretKeyGenerator.class) {
            if (instance == null) {
                instance = new SecretKeyGenerator();
            }
        }
        return instance;
    }

    public static SecretKey key() {
        return getInstance().key;
    }
}