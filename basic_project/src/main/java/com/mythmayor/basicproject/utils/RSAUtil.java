package com.mythmayor.basicproject.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by mythmayor on 2020/6/30.
 * 1、公钥加密、私钥解密用于信息加密
 * 2、私钥加密、公钥解密用于数字签名
 */
public class RSAUtil {

    private static final String KEY_RSA = "RSA";
    private static final String KEY_RSA_ECB_PKCS1Padding = "RSA/ECB/PKCS1Padding";
    //定义加密方式
    private static final String KEY_TRANSFORMATION = KEY_RSA_ECB_PKCS1Padding;
    private static final String KEY_ALGORITHM = KEY_RSA;
    //定义签名算法
    private final static String KEY_RSA_SIGNATURE = "MD5withRSA";
    //秘钥长度为1024
    private static final int MAX_ENCRYPT_1024 = 117;
    private static final int MAX_DECRYPT_1024 = 128;
    //秘钥长度为2048
    private static final int MAX_ENCRYPT_2048 = 245;
    private static final int MAX_DECRYPT_2048 = 256;
    //RSA最大加密大小
    private static final int MAX_ENCRYPT_BLOCK = MAX_ENCRYPT_1024;
    //RSA最大解密大小
    private static final int MAX_DECRYPT_BLOCK = MAX_DECRYPT_2048;

    public static void main(String[] args) throws Exception {
        String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtyKc9+0w/XPO7g9MsgIr\n" +
                "dZwhi9DKapcTl+Dlmyb6ESzGKPVlOavEVAKDZsL1CNcc1kM7WGTo/Fxu97aNT+QE\n" +
                "XKAJWfDPCA3yf9eUiAyRlYNePyDC71+NcAdx0Vphl3lgjU+gRAjqVVlK+ljuzu+f\n" +
                "yCn0zEflLmCI8FB4ulvdB7SP1Ye4DEpdxGT2FVp/zDCk5dAZ/uG8tWpLRe813MRo\n" +
                "YG9DiVC0+r6WoYxuH505TiXnfJKLL9+xbhgW3aByhM0cJwSraRe58a70Wga6iUJB\n" +
                "VONCE5BFdGrPOnxTUbtPkje1621tGQT3tUulbj+alCHbAnzPAbjJXG5DX9z0wcJB\n" +
                "mQIDAQAB";
        String privateKeyStr = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC3Ipz37TD9c87u\n" +
                "D0yyAit1nCGL0MpqlxOX4OWbJvoRLMYo9WU5q8RUAoNmwvUI1xzWQztYZOj8XG73\n" +
                "to1P5ARcoAlZ8M8IDfJ/15SIDJGVg14/IMLvX41wB3HRWmGXeWCNT6BECOpVWUr6\n" +
                "WO7O75/IKfTMR+UuYIjwUHi6W90HtI/Vh7gMSl3EZPYVWn/MMKTl0Bn+4by1aktF\n" +
                "7zXcxGhgb0OJULT6vpahjG4fnTlOJed8kosv37FuGBbdoHKEzRwnBKtpF7nxrvRa\n" +
                "BrqJQkFU40ITkEV0as86fFNRu0+SN7XrbW0ZBPe1S6VuP5qUIdsCfM8BuMlcbkNf\n" +
                "3PTBwkGZAgMBAAECggEAH696bx73yfJejCvTfj0H3epsl29Bhl2rQyzjLQv6Wjxf\n" +
                "8Lg8klTYvBxPNdlnYecuExhAzVNwSDGJ9Yv9OSRfaNM7qvcSKveWzG21QjDCg5be\n" +
                "GJgHgjkLFKYFGE7F8dpLTUfIl9zfBnNTmKsIbn9hKtWviCSsT3DeYI3bxYqdXHai\n" +
                "xi4yzSa5FZ/d81eJkJuei3GkOfnbdz5ZpAjFMN7MNJGOv7/vPsWXbfz9L9iPfILO\n" +
                "Ck+3QiUD5fuCUBLLBoFWEAraViQPMUK1si1sY7wZxw+qWfA0F96uMdYDNJIsJ0Fl\n" +
                "9+uXrcavUmHnhewZqzwjqN88C/1O7Kh0/2pm+qwRjQKBgQDxzU9vE/TjrtcCSwMv\n" +
                "mXCBQJ58N5/5hOFTzKlJc1d8QbiXiaXX7kxza/fsSfQ0Hrb7nWbQDbS+gTAIi1io\n" +
                "Sm6kmsltfQh9jj8Wkr0MqPSDGSf8ddu90t/ryR3z9FkU8AYCmgK0YnE/sVIzYEir\n" +
                "qybzn8KKGYv1BdM9TY8O7+j8uwKBgQDB43FQl9nLiLKx0NrTettuazB+stKmvYg/\n" +
                "MScw8OMtvi4NNUWyP9WsvCbem2UUJvXZ8WsjA13b5aCjbdJpAUOFFdzFeWejwtOo\n" +
                "S9WZK2f3SBf8c/44YmCZseONkQ3GYA3GhhlLqIDdZsQZLEs+qreats56NL+DiUJ2\n" +
                "TevE9/UfuwKBgFcDqzIt3FKS+Iy6scrPTM02k9HfspH4X53oaIMmHrV4cRUXU7cA\n" +
                "6kIe+HjvpBGde/vQCM+WHxTvKHnqq1zkbCyQByx/ci68obdd1upXYqZKxzjysMD+\n" +
                "wdX36e+AXw5J0d/dzFtdkaN6DDqwy3X7gWjqdPd9egASuJG292RIsW9BAoGAU3wW\n" +
                "n6gy7HO41d3jS2o8L8hRwkxHbepwTx1gDa+7ckfQVnmYwA7Cd7UwW2u30zyPYb+U\n" +
                "7SeW84dlatlv3yzfIN/wBsumt5m9P1sf5usMwio1wctELI0XoTY6kAtkTgWwCMpW\n" +
                "+//bNPn5+AjdgWdK1YBfWbl6uS2SA5S7rz1BO98CgYBE3seyiScaKn7j0urTwFfr\n" +
                "LcFmpGNPatGXBPqHxXRF+zwASdLVIfGAt1wufjCEAlzBE0e3FrN92iVk9Q3R2MyK\n" +
                "nwAdzagADD0+3oL7wbLnm5Oh4LaD67ycqiB6BQVw3vzw7yPVJiyNusTB+9V2T/KC\n" +
                "NA6jFUJQDPvn3r7RsD8z4w==";
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        PublicKey publicKey = RSAUtil.loadPublicKey(publicKeyStr);
        PrivateKey privateKey = RSAUtil.loadPrivateKey(privateKeyStr);
        String encryptResult = encryptData(s, publicKey);
        System.out.println("加密结果：" + encryptResult);
        String decryptResult = decryptData(encryptResult, privateKey);
        System.out.println("解密结果：" + new String(decryptResult));
    }

    /**
     * 随机生成RSA密钥对(默认密钥长度为1024)
     *
     * @return
     */
    public static KeyPair generateRSAKeyPair() {
        return generateRSAKeyPair(1024);
    }

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048<br>
     *                  一般1024
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * RSA加密过程
     *
     * @param plainTextData 明文数据
     * @param publicKey     公钥
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static String encryptData(byte[] plainTextData, PublicKey publicKey) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(KEY_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return base64Encode(cipher.doFinal(plainTextData));
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * RSA解密
     *
     * @param cipherData 密文数据
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decryptData(byte[] cipherData, PrivateKey privateKey) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(KEY_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(cipherData));
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    public static String encryptData(String encryptData, PublicKey publicKey) throws Exception {
        return encryptByPublicKey(encryptData, publicKey, MAX_ENCRYPT_BLOCK);
    }

    public static String decryptData(String decryptData, PrivateKey privateKey) throws Exception {
        return decryptByPrivateKey(decryptData, privateKey, MAX_DECRYPT_BLOCK);
    }

    /**
     * 公钥加密
     *
     * @param encryptData 加密的数据
     * @param publicKey   公钥
     * @param length      加密长度
     * @return 加密结果
     * @throws Exception
     */
    public static String encryptByPublicKey(String encryptData, PublicKey publicKey, int length) throws Exception {
        // 取得待加密数据
        byte[] data = encryptData.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance(KEY_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > length) {
                cache = cipher.doFinal(data, offSet, length);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * length;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return base64Encode(encryptedData);
    }

    /**
     * 私钥解密
     *
     * @param decryptData 解密数据
     * @param privateKey  私钥
     * @param length      加密长度
     * @return 解密结果
     * @throws Exception
     */
    public static String decryptByPrivateKey(String decryptData, PrivateKey privateKey, int length) throws Exception {
        // 获得待解密数据
        byte[] data = base64Decode(decryptData);
        Cipher cipher = Cipher.getInstance(KEY_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > length) {
                cache = cipher.doFinal(data, offSet, length);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * length;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 私钥加密
     *
     * @param encryptData 加密的数据
     * @param privateKey  私钥
     * @param length      加密长度
     * @return 加密结果
     * @throws Exception
     */
    public static String encryptByPrivateKey(String encryptData, PrivateKey privateKey, int length) throws Exception {
        // 取得待加密数据
        byte[] data = encryptData.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance(KEY_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > length) {
                cache = cipher.doFinal(data, offSet, length);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * length;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return base64Encode(encryptedData);
    }

    /**
     * 公钥解密
     *
     * @param decryptData 解密数据
     * @param publicKey   公钥
     * @param length      加密长度
     * @return 解密结果
     * @throws Exception
     */
    public static String decryptByPublicKey(String decryptData, PublicKey publicKey, int length) throws Exception {
        // 获得待解密数据
        byte[] data = base64Decode(decryptData);
        Cipher cipher = Cipher.getInstance(KEY_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > length) {
                cache = cipher.doFinal(data, offSet, length);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * length;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 用私钥对加密数据进行签名
     *
     * @param encryptData 加密数据
     * @param privateKey  私钥
     * @return 数字签名
     */
    public static String sign(String encryptData, PrivateKey privateKey) {
        String str = "";
        try {
            //将私钥加密数据字符串转换为字节数组
            byte[] data = encryptData.getBytes();
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initSign(privateKey);
            signature.update(data);
            str = base64Encode(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 校验数字签名
     *
     * @param encryptData 加密数据
     * @param publicKey   公钥
     * @param sign        数字签名
     * @return 校验成功返回true，失败返回false
     */
    public static boolean verify(String encryptData, PublicKey publicKey, String sign) {
        boolean flag = false;
        try {
            //将私钥加密数据字符串转换为字节数组
            byte[] data = encryptData.getBytes();
            // 用公钥验证数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initVerify(publicKey);
            signature.update(data);
            flag = signature.verify(base64Decode(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * BASE64 编码
     *
     * @param data 需要Base64编码的字节数组
     * @return 字符串
     */
    public static String base64Encode(byte[] data) {
        return Base64Util.encode(data);
    }

    /**
     * BASE64 解码
     *
     * @param data 需要Base64解码的字符串
     * @return 字节数组
     */
    public static byte[] base64Decode(String data) {
        return Base64Util.decode(data);
    }

    /**
     * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 通过私钥byte[]将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 使用N、e值还原公钥
     *
     * @param modulus
     * @param publicExponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String modulus, String publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 使用N、d值还原私钥
     *
     * @param modulus
     * @param privateExponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String modulus, String privateExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger(modulus);
        BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = base64Decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从字符串中加载私钥<br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = base64Decode(privateKeyStr);
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            // 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(InputStream in) throws Exception {
        try {
            return loadPublicKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(InputStream in) throws Exception {
        try {
            return loadPrivateKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static String readKey(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
                sb.append('\r');
            }
        }
        return sb.toString();
    }

    /**
     * 打印公钥信息
     *
     * @param publicKey
     */
    public static void printPublicKeyInfo(PublicKey publicKey) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        System.out.println("----------RSAPublicKey----------");
        System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());
        System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
        System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
    }

    public static void printPrivateKeyInfo(PrivateKey privateKey) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        System.out.println("----------RSAPrivateKey ----------");
        System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());
        System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
        System.out.println("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());
    }
}
