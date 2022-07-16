package com.wung.concurrent.util;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport 线程阻塞工具类。
 *
 * @author wangmc
 * @date 2022-07-16
 */
public class LockSupportDemo {
    private static Object u = new Object();
    private static ChannelObjectDemo t1 = new ChannelObjectDemo("t1");
    private static ChannelObjectDemo t2 = new ChannelObjectDemo("t2");

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }

    static class ChannelObjectDemo extends Thread {

        public ChannelObjectDemo(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("In " + getName());
                LockSupport.park();
                System.out.println(getName() + " end");
            }
        }
    }
}


// out
//In t1
//t1 end
//In t2
//t2 end