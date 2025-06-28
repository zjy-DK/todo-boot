package com.todo.util;

import com.todo.base.UserException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 断言工具类
 *
 * @author zjy
 * @date 2022/11/7
 */
public class AssertUtil {

    /**
     * 对象验空
     */
    public static void isNull(@Nullable Object o, String message) {
        if (Objects.isNull(o)) {
            throw new UserException(message);
        }
    }

    /**
     * 对象非空
     */
    public static void isNotNull(@Nullable Object o, String message) {
        if (!Objects.isNull(o)) {
            throw new UserException(message);
        }
    }

    /**
     * 对象非空
     */
    public static void isNotNull(@Nullable Collection<?> o, String message) {
        if (!CollectionUtils.isEmpty(o)) {
            throw new UserException(message);
        }
    }

    /**
     * 集合验空
     */
    public static void isNull(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new UserException(message);
        }
    }

    /**
     * MAP验空
     */
    public static void isNull(@Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new UserException(message);
        }
    }

    /**
     * String验空
     */
    public static void isNull(@Nullable String str, String message) {
        if (null == str || str.trim().isEmpty()) {
            throw new UserException(message);
        }
    }

    /**
     * 字符串判空
     */
    public static void isNull(@Nullable Object[] o, String message) {
        if (o == null || o.length == 0) {
            throw new UserException(message);
        }
    }

    /**
     * 对象对比验证
     * 等同于 o!=o1&&o!=o2 throw new BusinessException(message)
     */
    public static void notEquals(String message, Object o, Object... to) {
        int i = 0;
        for (Object o1 : to) {
            if (o != o1) {
                i++;
            }
        }
        if (to.length == i) {
            throw new UserException(message);
        }
    }

    /**
     * double对比等同于 o==o1||o==o2 throw new BusinessException(message);
     */
    public static void equals(String message, double o, double to) {
        if (Double.compare(o, to) != 0) {
            throw new UserException(message);
        }
    }

    /**
     * 对象对比验证
     * 等同于 o==o1||o==o2 throw new BusinessException(message);
     */
    public static void equals(String message, Object o, Object... to) {
        for (Object o1 : to) {
            if (o == o1) {
                throw new UserException(message);
            }
        }
    }

    /**
     * 字符串比较
     */
    public static void notEquals(String message, String str, String srt1) {
        if (!str.equals(srt1)) {
            throw new UserException(message);
        }
    }

    /**
     * Double对比验证
     * 等同于 o!=o1&&o!=o2 throw new BusinessException(message)
     */
    public static void notEquals(String message, Double o, Double... to) {
        int i = 0;
        for (Double o1 : to) {
            if (!Objects.equals(o, o1)) {
                i++;
            }
        }
        if (to.length == i) {
            throw new UserException(message);
        }
    }
}
