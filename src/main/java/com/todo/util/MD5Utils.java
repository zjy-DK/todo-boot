package com.todo.util;

import com.todo.base.BaseConstant;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public class MD5Utils {


    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte[] s = m.digest();
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < s.length; i++) {
                result.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            log.error("error",e);
        }
        return "";
    }

    /**
     * 判断输入的密码和数据库中保存的MD5密码是否一致
     *
     * @param inputPassword 输入的密码
     * @param md5DB         数据库保存的密码
     * @return
     */
    public static boolean passwordIsTrue(String inputPassword, String md5DB) {

        String md5 = string2MD5(inputPassword);
        return md5DB.equals(md5);
    }


    // 测试主函数
    public static void main(String args[]) {
        String password = "pkO7p&l6";
        String concat = password.concat(BaseConstant.PASSWORD_SUFFIX);
        String s = string2MD5(concat);
        System.out.println(s);
        String s1 = string2MD5(s);
        System.out.println(s1);
    }
}
