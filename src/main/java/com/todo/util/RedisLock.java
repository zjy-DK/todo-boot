package com.todo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * 基于单点redis实现的分布式锁
 *
 * @author zjy
 * @date 2025/02/13  16:40
 */
@Slf4j
@Component
public class RedisLock {

    @Resource
    RedisTemplate redisTemplate;

    private static final String LOCK_HEARD = "lock:";

    public boolean tryLock(String lockKey, String lockValue, long expireTime) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(LOCK_HEARD + lockKey, lockValue, expireTime,
                java.util.concurrent.TimeUnit.SECONDS);
        return result != null && result;
    }

    public boolean unlock(String lockKey, String lockValue) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
        try {
            Object result = redisTemplate.execute(redisScript, Collections.singletonList(LOCK_HEARD + lockKey), lockValue);
            if (result.equals(1)) {
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}
