package com.wung.concurrent.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 支持延迟的无界阻塞队列
 *
 * @author wangmc
 * @date 2022-08-08
 */
public class DelayQueueDemo {

    static BlockingQueue<Delayed> queue = new DelayQueue<>();

    public static void main(String[] args) throws InterruptedException {
        queue.add(new MyDelay<>(8, "First Job"));
        queue.add(new MyDelay<>(10, "Second Job"));
        queue.add(new MyDelay<>(3, "Third Job"));

        while (!queue.isEmpty()) {
            Delayed take = queue.take();
            System.out.println(take);
        }
    }

    /**
     * 元素必须实现 Delayed 接口
     * @param <T>
     */
    static class MyDelay<T> implements Delayed {
        // 延迟时间
        private long delayTime;
        // 过期时间
        private long expire;
        // 数据
        private T data;

        public MyDelay(long delayTime, T data) {
            this.delayTime = delayTime;
            this.expire = System.currentTimeMillis() + delayTime;
            this.data = data;
        }

        /**
         * 返回剩余时间 = 到期时间 - 当前时间
         *
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /**
         * 优先级规则：两个任务比较，时间短的优先执行
         */
        @Override
        public int compareTo(Delayed o) {
            long f = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
            return (int) f;
        }

        @Override
        public String toString() {
            return "MyDelay{" +
                    "delayTime=" + delayTime +
                    ", expire=" + expire +
                    ", data=" + data +
                    '}';
        }
    }
}


// out
//MyDelay{delayTime=3, expire=1659922255350, data=Third Job}
//MyDelay{delayTime=8, expire=1659922255351, data=First Job}
//MyDelay{delayTime=10, expire=1659922255353, data=Second Job}
