package com.todo.config.async;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author zjy
 * @date 2024/12/08  10:44
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 核心线程数（corePoolSize）：
     * 线程池中保持存活的最小线程数量，即使它们处于空闲状态。
     * 默认情况下，这些线程不会超时关闭。
     */
    private static final int CORE_POOL_SIZE = 5;

    /**
     * 最大线程数（maximumPoolSize）：
     * 线程池中允许的最大线程数量。
     * 当任务队列已满且当前线程数小于 maximumPoolSize 时，会创建新线程处理任务。
     */
    private static final int MAXIMUM_POOL_SIZE = 10;

    /**
     * 空闲线程存活时间（keepAliveTime）：
     * 非核心线程在没有任务执行时的最大等待时间，超过这个时间将被销毁。
     * 若设置 allowCoreThreadTimeOut(true)，该参数也适用于核心线程。
     */
    private static final long KEEP_ALIVE_TIME = 60;

    /**
     * 存活时间单位（unit）：
     * 表示 keepAliveTime 的时间单位，例如秒、毫秒等。
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    /**
     * 任务队列（workQueue）：
     * 用于存放待执行任务的阻塞队列。
     * 当核心线程都在忙碌时，新提交的任务会被放入此队列中等待执行。
     */
    private static final BlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(10);

    /**
     * 线程工厂（threadFactory）：
     * 用于创建新线程的工厂类，可以自定义线程名称、优先级等属性。
     * 此处使用 ThreadFactoryBuilder 设置了线程池的名称前缀，便于日志调试。
     */
    private static final ThreadFactory THREAD_FACTORY = ThreadFactoryBuilder.create()
            .setNamePrefix("todoThreadPool")
            .build();

    /**
     * 拒绝策略（handler）：
     * 当任务无法提交时（如线程池和队列都已满），由该策略决定如何处理拒绝的任务。
     * DiscardPolicy 表示直接丢弃任务，不抛出异常。
     */
    private static final RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.DiscardPolicy();


    /**
     * 用给定的初始参数创建一个新的ThreadPoolExecutor。
     */
    @Bean
    public ThreadPoolExecutor myThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                WORK_QUEUE,
                THREAD_FACTORY,
                HANDLER);
    }
}
