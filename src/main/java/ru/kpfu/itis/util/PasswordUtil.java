package ru.kpfu.itis.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import jakarta.xml.bind.DatatypeConverter;

public class PasswordUtil {

    private PasswordUtil() {
    }

    public static String encrypt(String password){
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();

            return DatatypeConverter.printHexBinary(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
