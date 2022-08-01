package com.wung.concurrent.util;


import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 利用 ConcurrentLinkedQueue 实现生产者消费者模式。
 *
 * @author wangmc
 * @date 2022-08-01
 */
public class ConcurrentLinkedQueueDemo {

    private static final ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();


    static class Producer extends Thread {

        private String name;

        public Producer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                queue.offer(i);
                System.out.println(name + " 添加元素：" + i);

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }

    }

    static class Consumer extends Thread {
        private String name;

        public Consumer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (;;) {
                Integer i = queue.poll();
                System.out.println(name + " 取得元素：" + i);

                // 没有元素时，退出
                if (i == null) {
                    break;
                }

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        Producer p1 = new Producer("p1");
        Producer p2 = new Producer("p2");
        Consumer c1 = new Consumer("c1");

        p1.start();
        p2.start();
        c1.start();


    }

}

// out
//p1 添加元素：0
//c1 取得元素：0
//p2 添加元素：0
//p1 添加元素：1
//p2 添加元素：1
//c1 取得元素：0
//p1 添加元素：2
//p2 添加元素：2
//c1 取得元素：1
//p2 添加元素：3
//p1 添加元素：3
//c1 取得元素：1
//p2 添加元素：4
//p1 添加元素：4
//c1 取得元素：2
//p2 添加元素：5
//p1 添加元素：5
//c1 取得元素：2
//p2 添加元素：6
//p1 添加元素：6
//c1 取得元素：3
//p2 添加元素：7
//p1 添加元素：7
//c1 取得元素：3
//p2 添加元素：8
//p1 添加元素：8
//c1 取得元素：4
//p2 添加元素：9
//p1 添加元素：9
//c1 取得元素：4
//c1 取得元素：5
//c1 取得元素：5
//c1 取得元素：6
//c1 取得元素：6
//c1 取得元素：7
//c1 取得元素：7
//c1 取得元素：8
//c1 取得元素：8
//c1 取得元素：9
//c1 取得元素：9
//c1 取得元素：null
