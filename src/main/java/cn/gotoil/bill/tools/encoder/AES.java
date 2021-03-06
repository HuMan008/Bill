/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.common.tools.encoder.AES
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package cn.gotoil.bill.tools.encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

public class AES {

    private static final String CipherAES_CBC_NoPadding = "AES/CBC/NoPadding";

    private static final String CipherAES_CBC_PKCS5Padding = "AES/CBC/PKCS5Padding";

    /**
     * @param data
     * @param secret
     * @param iv
     * @return
     */
    public static byte[] decrypt(byte[] data, String secret, byte[] iv) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(),
                    "AES");
            Cipher cipher = Cipher.getInstance(CipherAES_CBC_NoPadding);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data
     * @param secret
     * @param iv
     * @return
     */
    public static byte[] encrypt(byte[] data, String secret, byte[] iv) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(),
                    "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(CipherAES_CBC_PKCS5Padding);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param data
     * @param secret
     * @return
     */
    public static byte[] simpleDecrypt(String data, String secret) {
        byte[] iv = simpleIVInit(secret);

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(),
                    "AES");
            Cipher cipher = Cipher.getInstance(CipherAES_CBC_PKCS5Padding);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(Base64.getDecoder().decode(URLDecoder.decode(data,
                    "UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param data
     * @param secret
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String simpleEncrypt(byte[] data, String secret)
            throws UnsupportedEncodingException {
        byte[] iv = simpleIVInit(secret);

        byte[] result = encrypt(data, secret, iv);
        return URLEncoder.encode(Base64.getEncoder().encodeToString(result), "UTF-8");
    }


    /**
     * @param secret
     * @return
     */
    private static byte[] simpleIVInit(String secret) {
        byte[] iv = new byte[16];
        for (byte i = 0; i < 16; i++) {
            if (i < 4) {
                iv[i] = (byte) secret.charAt(i * 2);
            } else if (i < 12) {
                iv[i] = (byte) secret.charAt(i + 8);
            } else {
                iv[i] = (byte) secret.charAt(secret.length() - 16 + i);
            }
        }

        return iv;
    }
}
