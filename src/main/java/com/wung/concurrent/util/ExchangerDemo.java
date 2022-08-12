package com.wung.concurrent.util;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于线程间互换数据。
 * 它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。
 * 这两个线程通过 exchange方法交换数据，如果第一个线程先执行exchange()方法，它会一直等待第二个线程也执行exchange方法，
 * 当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产 出来的数据传递给对方。
 *
 * 可以看做 SynchronousQueue 的双向形式。
 *
 * 可以用于遗传算法。
 *
 * @author wangmc
 * @date 2022-08-12
 */
public class ExchangerDemo {
    static final Exchanger<String> EXCHANGER = new Exchanger<>();


    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            String a = "我来自:" + Thread.currentThread().getName();
            System.out.println(Thread.currentThread().getName() + "生产的数据为：" + a);
            String b = null;
            try {
                b = EXCHANGER.exchange(a);
                System.out.println(Thread.currentThread().getName() + "收到的数据为：" + b);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        };

        threadPool.execute(task);
        threadPool.execute(task);

        threadPool.shutdown();
    }

}

// out
//pool-1-thread-2生产的数据为：我来自:pool-1-thread-2
//pool-1-thread-1生产的数据为：我来自:pool-1-thread-1
//pool-1-thread-2收到的数据为：我来自:pool-1-thread-1
//pool-1-thread-1收到的数据为：我来自:pool-1-thread-2