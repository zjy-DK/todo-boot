package com.todo.config.idempotent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启接口幂等性校验
 * @author zjy
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 幂等性校验的过期时间，默认10秒
     */
    int expireTime() default 10;

    /**
     * 是否通过分布式锁提高校验等级
     */
    boolean distributedLock() default false;
}
