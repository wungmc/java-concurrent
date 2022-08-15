package com.wung.concurrent.locks.aqs;

import static java.lang.Thread.sleep;

/**
 * TwinsLock 应用
 *
 * @author wangmc
 * @date 2022-08-15
 */
public class TwinsLockDemo {

    private static final TwinsLock lock = new TwinsLock();

    static class Worker extends Thread {

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }

        for (int i = 0; i < 20; i++) {
            sleep(1000);
            System.out.println();
        }

    }
}
