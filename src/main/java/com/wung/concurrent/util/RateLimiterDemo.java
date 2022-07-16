package com.wung.concurrent.util;

import com.google.common.util.concurrent.RateLimiter;

/**
 * Guava 的限流工具类 RateLimiter 的简单使用。
 *
 * 限流算法一般有：
 * 1. 计数固定窗口（存在边界时间上的请求无法控制；一段时间内（不超过时间窗口）系统服务不可用）
 * 2. 计数滑动窗口（计数固定窗口的升级版，解决了边界时间上的请求无法控制的问题）
 * 3. 漏桶算法（流量曲线平滑，但是无法应对流量突发的情况）
 * 4. 令牌桶算法（即可以限流，又可以应对一定的流量突发）
 *
 * RateLimiter 使用的就是令牌桶算法。
 *
 * @author wangmc
 * @date 2022-07-17
 */
public class RateLimiterDemo {
    // 限制每秒只能处理 2 个请求
    static RateLimiter limiter = RateLimiter.create(2);

    public static void main(String[] args) {
        // 模拟 50 个请求
        for (int i = 0; i < 50; i++) {
            // 获取一个令牌，如果桶是空的，则阻塞
            limiter.acquire();
            // 尝试获取一个令牌，如果桶是空的（tryAcquire会立即返回 false），则丢弃任务
//            if (!limiter.tryAcquire()) {
//                continue;
//            }

            new Thread(() -> System.out.println(System.currentTimeMillis())).start();
        }
    }
}

// out
// 如果不加 limiter.acquire(), 则程序1毫秒不到就运行完了。
// 加了 limiter.acquire() 后，差不多每 500 毫秒运行完一个线程，即每秒处理2个。
// 改为使用 limiter.tryAcquire()) 后，因为for循环太快了，肯定不到500毫秒，所以最终只有1线程运行完了，其他都被丢弃了。