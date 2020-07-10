package com.mythmayor.basicproject.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mythmayor on 2020/6/30.
 * MD5加密工具类
 */
public class MD5Util {
    /**
     * 进行MD5的加密运算
     *
     * @param text 要进行MD5加密的字符串
     * @return 经过MD5加密后的字符串
     */
    public static String encode(String text) {
        try {
            // MessageDigest专门用于加密的类
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] result = messageDigest.digest(text.getBytes()); // 得到加密后的字符组数
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                int num = b & 0xff; // 这里的是为了将原本是byte型的数向上提升为int型，从而使得原本的负数转为了正数
                String hex = Integer.toHexString(num); //这里将int型的数直接转换成16进制表示
                //16进制可能是为1的长度，这种情况下，需要在前面补0，
                if (hex.length() == 1) {
                    sb.append(0);
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
