package com.duozhuan.bitalk.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class EncryptUtil {
    public static String encryptPassword(String password) {
        if (password != null && password.length() > 0) {
            String base64Str = Base64.encodeToString(password.getBytes(), Base64.DEFAULT);
            String md5Str = str2MD5(password);
            return md5Str.substring(0, 16) + base64Str + md5Str.substring(16);
        }
        return "";
    }

    public static String decryptPassword(String password) {
        if (password != null && password.length() > 32) {
            String base64Str = password.substring(16, password.length() - 16);
            try {
                return new String(Base64.decode(base64Str, Base64.DEFAULT), "UTF-8");
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * md5加密字符串
     *
     * @param s 待加密的字符串
     * @return
     */
    public static String str2MD5(String s) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * sha1加密字符串
     *
     * @param info 待加密字符串
     * @return
     */
    public static String encryptToSHA(String info) {
        byte[] digesta = null;
        try {
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            alga.update(info.getBytes());
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String hs = "", stmp;
        for (int i = 0; i < digesta.length; i++) {
            stmp = (Integer.toHexString(digesta[i] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs;
    }
}
