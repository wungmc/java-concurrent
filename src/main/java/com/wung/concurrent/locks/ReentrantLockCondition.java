package com.wung.concurrent.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 对于 ReentrantLock 就类似 wait/notify 对于 synchronized 。
 *
 * 利用 Condition 就可以让线程在合适的时间等待，或者在某个特定的时刻得到通知，继续执行。
 *
 * 比如，ArrayBlockingQueue 内部就使用了 ReentrantLock 和 Condition。
 *
 * @author wangmc
 * @date 2022-07-09
 */
public class ReentrantLockCondition implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {
        // 该线程运行后，先获得可重入锁
        lock.lock();
        try {
            // 让该线程等待在 condition 对象上（会释放lock）！直到有其他线程调用了 signal/signalAll 或者发生了中断
            condition.await();
            // 等待结束后，去获得锁，然后继续运行
            System.out.println("do something");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockCondition task = new ReentrantLockCondition();
        Thread t = new Thread(task);

        // 该线程启动后，立即获得lock，然后再释放 lock 进入等待
        t.start();

        // main 线程休眠 2秒
        Thread.sleep(2000);
        // main 继续运行
        System.out.println("Main after sleep 2000");

        // main 获得 lock，因为接下来 main 要调用 signal，调用该方法，要求线程必须持有锁，所以要先加锁
        lock.lock();
        // 调 signal 方法，会唤醒等待在 condition 对象上的线程，也就是线程 t
        // 此时，线程 t 被唤醒后，去争夺锁，然后继续运行，打印"do something"
        condition.signal();

        // main 调用 signal 后必须释放锁，否则线程 t 会因无法拿到锁，而无法运行
        lock.unlock();
    }
}

// out
//Main after sleep 2000
//do something