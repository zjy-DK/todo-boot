package com.todo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

/**
 * 密码工具
 *
 * @author zjy
 * @date 2025/06/11  16:37
 */
public class PasswordUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String PASSWORD_FIELD = "password";
    private static final String MASK_VALUE = "*********";

    // 定义字符集
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGIT = "0123456789";
    private static final String SPECIAL = "!@#$%^&*";

    // 所有字符集合
    private static final String ALL_CHARACTERS = LOWER + UPPER + DIGIT + SPECIAL;

    // 随机数生成器
    private static final Random random = new Random();

    /**
     * 生成指定长度的强密码
     *
     * @param length 密码长度，建议 >= 8
     * @return 生成的密码字符串
     */
    public static String generateStrongPassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("密码长度至少为8位");
        }

        // 使用StringBuilder构建密码
        StringBuilder password = new StringBuilder(length);

        // 确保每种类型的字符至少有一个
        password.append(randomChar(LOWER));
        password.append(randomChar(UPPER));
        password.append(randomChar(DIGIT));
        password.append(randomChar(SPECIAL));

        // 填充剩余字符
        for (int i = 4; i < length; i++) {
            password.append(randomChar(ALL_CHARACTERS));
        }

        // 打乱顺序以避免前4个字符总是固定类型
        return shuffleString(password.toString());
    }

    // 从指定字符集中随机选择一个字符
    private static char randomChar(String charSet) {
        int index = random.nextInt(charSet.length());
        return charSet.charAt(index);
    }

    // 打乱字符串顺序
    private static String shuffleString(String input) {
        List<Character> chars = new ArrayList<>();
        for (char c : input.toCharArray()) {
            chars.add(c);
        }
        Collections.shuffle(chars, random);
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 将 JSON 字符串中的 password 字段值替换为 *********
     *
     * @param jsonStr 原始 JSON 字符串
     * @return 脱敏后的 JSON 字符串
     */
    public static String maskPasswordInJson(String jsonStr) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonStr);
            maskPassword(rootNode);
            return objectMapper.writeValueAsString(rootNode);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON string", e);
        }
    }

    private static void maskPassword(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Iterator<String> fieldNames = objectNode.fieldNames();

            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode childNode = objectNode.get(fieldName);

                if (fieldName.equalsIgnoreCase(PASSWORD_FIELD)) {
                    objectNode.put(fieldName, MASK_VALUE);
                } else if (childNode.isContainerNode()) {
                    // 如果是对象或数组，递归处理
                    maskPassword(childNode);
                }
            }
        } else if (node.isArray()) {
            node.forEach(PasswordUtil::maskPassword);
        }
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) throws Exception {
       for (int i = 0; i < 30; i++){
           String password = generateStrongPassword(10);
           System.out.println(password);
       }
    }
}
