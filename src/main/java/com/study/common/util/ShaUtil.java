package com.study.common.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaUtil {

    public static final String SHA_256 = "SHA-256";
    public static final String SHA_512 = "SHA-512";
    public static final String SHA3_256 = "SHA3-256";
    public static final String SHA3_512 = "SHA3-512";

    public static String shaAndBase64(String plainText, String Algorithms) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance(Algorithms);
        md.update(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeBase64String(md.digest());
    }
}
