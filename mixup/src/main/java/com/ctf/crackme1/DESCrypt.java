package com.ctf.crackme1;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by CwT on 16/3/19.
 */
public class DESCrypt {
    private static Cipher cipher = null;
    private static SecretKeyFactory mySecretKeyFactory = null;

    static {
        try {
            cipher = Cipher.getInstance("DES");
            mySecretKeyFactory = SecretKeyFactory.getInstance("DES");
        } catch (Exception e) {

        }
    }

    public static byte[] encrypt(String string, String keyString) {
        try {
            byte[] encryptionKey = keyString.getBytes("UTF8");
            KeySpec myKeySpec = new DESKeySpec(encryptionKey);
            SecretKey key = mySecretKeyFactory.generateSecret(myKeySpec);

            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] stringBytes = string.getBytes("UTF8");
            byte[] encryptedBytes = cipher.doFinal(stringBytes);

            return encryptedBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public static String decrypt(byte[] encrypted, String keyString) {
        String decrypted = null;
        try {
            byte[] encryptionKey = keyString.getBytes("UTF8");
            KeySpec myKeySpec = new DESKeySpec(encryptionKey);
            SecretKey key = mySecretKeyFactory.generateSecret(myKeySpec);

            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(encrypted);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
