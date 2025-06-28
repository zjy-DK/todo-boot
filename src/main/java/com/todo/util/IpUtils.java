package com.todo.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zjy
 * @date 2024/12/14  19:31
 */
@Slf4j
public class IpUtils {
    public static String getIpAddress(HttpServletRequest request) {
        String ip;

        // 多个代理可能会有多个值，用逗号分隔，第一个是真实IP
        String ipUtilsFlag = ",";

        // 优先检查 X-Forwarded-For
        ip = request.getHeader("X-Forwarded-For");
        if (isInvalidIp(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }

        if (isInvalidIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (isInvalidIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (isInvalidIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (isInvalidIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (isInvalidIp(ip)) {
            ip = request.getRemoteAddr();
        }

        // 如果是多级代理，取第一个IP
        if (ip != null && ip.contains(ipUtilsFlag)) {
            ip = ip.split(ipUtilsFlag)[0];
        }

        return ip;
    }

    private static boolean isInvalidIp(String ip) {
        return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
    }
}
