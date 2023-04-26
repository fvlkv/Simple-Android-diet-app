package com.example.projekt;

import android.util.Base64;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper {

    private static final String key = "aesEncryptionKey";
    private static final String initVector="encryptionIntVec";

    public static String encrypt(String value){
        try{
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"),"AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE,skeySpec,iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encrypted,Base64.DEFAULT);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
