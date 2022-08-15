package com.wung.concurrent.locks.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 利用 AQS 实现一个共享锁：同时允许最多两个线程访问，超过则被阻塞。
 *
 * @author wangmc
 * @date 2022-08-15
 */
public class TwinsLock implements Lock {

    private static final class Sync extends AbstractQueuedSynchronizer {
        public Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count must gt zero!");
            }
            setState(count);
        }

        /**
         * 共享锁要覆写 tryAcquireShared 方法
         *
         * @param reduceCount
         * @return
         */
        @Override
        protected int tryAcquireShared(int reduceCount) {
            if (reduceCount <= 0) {
                throw new IllegalArgumentException("reduceCount must gt zero!");
            }
            for (;;) {
                int current = getState();
                int remain = current - reduceCount;
                if (remain < 0 || compareAndSetState(current, remain)) {
                    // 注意：小于0表示，获取失败
                    return remain;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            if (returnCount <= 0) {
                throw new IllegalArgumentException("returnCount must gt zero!");
            }
            for (;;) {
                int current = getState();
                int remain = current + returnCount;
                if (compareAndSetState(current, remain)) {
                    return true;
                }
            }
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final Sync sync = new Sync(2);

    @Override
    public void lock() {
        sync.acquireShared(1);
//        System.out.println(Thread.currentThread().getName() + "获取锁成功");
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
//        System.out.println(Thread.currentThread().getName() + "释放锁成功");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) > 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

}
