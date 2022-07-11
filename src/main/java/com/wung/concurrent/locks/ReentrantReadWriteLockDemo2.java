package com.wung.concurrent.locks;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 模拟读多写少的场景下，分别使用 ReentrantLock 和 ReentrantReadWriteLock 实现，比较下耗时情况。
 *
 * 使用可重入锁时，运行完大约需要 20 秒，因为读写线程全部是串行的；
 * 切换到读写锁后，运行大约只需要 2～3 秒，此时所有读线程都是并行的。
 *
 * @author wangmc
 * @date 2022-07-10
 */
public class ReentrantReadWriteLockDemo2 {

    private static ReentrantLock lock = new ReentrantLock();

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    private int value;

    public int read(Lock lock) throws InterruptedException {
        lock.lock();
        try {
            // 模拟耗时
            Thread.sleep(1000);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void write(Lock lock, int newValue) throws InterruptedException {
        lock.lock();
        try {
            // 模拟耗时
            Thread.sleep(1000);
            value = newValue;
        } finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        ReentrantReadWriteLockDemo2 demo = new ReentrantReadWriteLockDemo2();
        Runnable read = () -> {
            try {
                // 使用可重入锁
//                demo.read(lock);

                // 使用读写锁
                demo.read(readLock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable write = () -> {
            try {
                // 使用可重入锁
//                demo.write(lock, new Random().nextInt());

                // 使用读写锁
                demo.write(writeLock, new Random().nextInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 模拟 18 个读线程
        for (int i = 0; i < 18; i++) {
            new Thread(read).start();
        }

        // 模拟 2 个写线程
        for (int i = 0; i < 2; i++) {
            new Thread(write).start();
        }

    }

}
