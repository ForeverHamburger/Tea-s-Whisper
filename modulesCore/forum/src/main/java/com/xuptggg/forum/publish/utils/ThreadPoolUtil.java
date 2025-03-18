package com.xuptggg.forum.publish.utils;

import java.util.concurrent.*;

// 线程池工具类，使用单例模式
public class ThreadPoolUtil {
    // 线程池核心线程数
    private static final int CORE_POOL_SIZE = 5;
    // 线程池最大线程数
    private static final int MAX_POOL_SIZE = 10;
    // 线程空闲时间，超过该时间空闲线程将被回收
    private static final long KEEP_ALIVE_TIME = 60L;
    // 时间单位，这里是秒
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    // 任务队列容量
    private static final int QUEUE_CAPACITY = 100;

    // 单例实例
    private static volatile ThreadPoolUtil instance;
    // 线程池实例
    private final ExecutorService threadPool;

    // 私有构造函数，防止外部实例化
    private ThreadPoolUtil() {
        // 创建一个有界阻塞队列
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        // 创建线程池
        threadPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                workQueue,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    // 获取单例实例的方法，使用双重检查锁定机制
    public static ThreadPoolUtil getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolUtil.class) {
                if (instance == null) {
                    instance = new ThreadPoolUtil();
                }
            }
        }
        return instance;
    }

    // 提交任务到线程池执行
    public void execute(Runnable task) {
        threadPool.execute(task);
    }

    // 提交有返回值的任务到线程池执行
    public <T> Future<T> submit(Callable<T> task) {
        return threadPool.submit(task);
    }

    // 关闭线程池
    public void shutdown() {
        threadPool.shutdown();
    }

    // 判断线程池是否已关闭
    public boolean isShutdown() {
        return threadPool.isShutdown();
    }
}