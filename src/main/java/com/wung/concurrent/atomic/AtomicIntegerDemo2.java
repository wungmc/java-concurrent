package com.wung.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并发环境下原子更新整型
 *
 * @author wangmc
 * @date 2022-08-11
 */
public class AtomicIntegerDemo2 {

    static AtomicInteger integer = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        int size = 10;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new AddThread();
        }

        for (int i = 0; i < size; i++) {
            threads[i].start();
        }

        for (int i = 0; i < size; i++) {
            threads[i].join();
        }

        System.out.println(integer);

    }

    static class AddThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                integer.incrementAndGet();
            }
        }
    }

}

// out
// 100000
