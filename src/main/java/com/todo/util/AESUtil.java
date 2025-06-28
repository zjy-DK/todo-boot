package com.todo.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES加密工具
 */
public class AESUtil {
    /**
     * 加密算法AES
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 算法名称/加密模式/数据填充方式
     * 默认：AES/ECB/PKCS5Padding
     */
    private static final String ALGORITHMS = "AES/ECB/PKCS5Padding";

    /**
     * AES 加密
     *
     * @param content    加密的字符串
     * @param encryptKey key值
     */
    public static String encrypt(String content, String encryptKey) throws Exception {
        //设置Cipher对象
        Cipher cipher = Cipher.getInstance(ALGORITHMS);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), KEY_ALGORITHM));

        //调用doFinal
        // 转base64
        return Base64.encodeBase64String(cipher.doFinal(content.getBytes(StandardCharsets.UTF_8)));

    }

    /**
     * AES 解密
     *
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     */
    public static String decrypt(String encryptStr, String decryptKey) throws Exception {
        //base64格式的key字符串转byte
        byte[] decodeBase64 = Base64.decodeBase64(encryptStr);


        //设置Cipher对象
        Cipher cipher = Cipher.getInstance(ALGORITHMS);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), KEY_ALGORITHM));
        //调用doFinal解密
        return new String(cipher.doFinal(decodeBase64),StandardCharsets.UTF_8);

    }
}

