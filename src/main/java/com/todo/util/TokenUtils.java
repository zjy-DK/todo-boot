package com.todo.util;


import cn.hutool.json.JSONUtil;
import com.todo.domain.User;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author zjy
 * @date 2024/12/11  09:45
 */
@Slf4j
public class TokenUtils {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String SECRET_KEY = "p5VS06zmTUp20Fh8WNECM9+Px+84UXOG7+BnfQMqvTor1TfcaOjRufNmLvEB+vfb";

    /**
     * 生成Token
     *
     * @return token
     */
    public static String generateToken(User user) {
        try {
            AssertUtil.isNull(user, "用户信息不能为空");
            AssertUtil.isNull(user.getId(), "用户ID不能为空");
            String payload = user.getId() + ":" + System.currentTimeMillis();
            // 创建 HMAC-SHA256 密钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), HMAC_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(secretKeySpec);
            // 计算签名
            byte[] signatureBytes = mac.doFinal(payload.getBytes());
            // 使用 Base64 编码签名并返回Token
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error("生成Token失败，user:{}", JSONUtil.toJsonStr(user), e);
            return null;
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(4);
        String token = generateToken(user);
        System.out.println(token);
    }
}

