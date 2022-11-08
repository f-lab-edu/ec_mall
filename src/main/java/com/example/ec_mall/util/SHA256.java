package com.example.ec_mall.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public static String encrypt(String str)  {

        String SHA;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());

            byte[] bytes = md.digest();
            StringBuilder builder = new StringBuilder();

            for (byte b : bytes) {
                builder.append(String.format("%02x", b));
            }
            SHA = builder.toString();
        }
        catch (NoSuchAlgorithmException e){
            throw new RuntimeException("암호화 에러", e);
        }
       return SHA;
    }
}