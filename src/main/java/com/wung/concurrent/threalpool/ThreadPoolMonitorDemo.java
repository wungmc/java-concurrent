package com.wung.concurrent.threalpool;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * 通过继承线程池 ThreadPoolExecutor， 并实现 beforeExecute，afterExecute，terminated 方法，
 * 来对线程池进行监控。
 *
 * @author wangmc
 * @date 2022-08-12
 */
public class ThreadPoolMonitorDemo {

    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(3, 3, 0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行：" + ((MyTask)r).getName());
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                System.out.println("执行结束：" + ((MyTask)r).getName());
            }

            @Override
            protected void terminated() {
                super.terminated();
                System.out.println("线程池退出.");
            }
        };

        for (int i = 0; i < 3; i++) {
            threadPool.execute(new MyTask("Task-" + i));
        }

        threadPool.shutdown();

    }

    static class MyTask implements Runnable {
        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {
            System.out.println("正在执行：" + name + " 任务，线程ID：" + Thread.currentThread().getId());

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

}

// out
//准备执行：Task-1
//准备执行：Task-2
//准备执行：Task-0
//正在执行：Task-0 任务，线程ID：11
//正在执行：Task-2 任务，线程ID：13
//正在执行：Task-1 任务，线程ID：12
//执行结束：Task-2
//执行结束：Task-1
//执行结束：Task-0
//线程池退出.
