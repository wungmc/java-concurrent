package com.wung.concurrent.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用内置的 FutureTask 实现异步调用。
 *
 * @author wangmc
 * @date 2022-08-18
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MySyncTask("A"));
        new Thread(futureTask).start();

        System.out.println("main 继续做别的事情");
        System.out.println("结果：" + futureTask.get());
    }

    static class MySyncTask implements Callable<String> {

        private String repeatStr;

        public MySyncTask(String repeatStr) {
            this.repeatStr = repeatStr;
        }

        @Override
        public String call() throws Exception {
            String result = "";
            try {
                // 模拟任务耗时
                System.out.println("任务开始...");
                Thread.sleep(1000);

                for (int i = 0; i < 10; i++) {
                    result += repeatStr;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("任务结束.");
            return result;
        }
    }
}

// out
//任务开始...
//main 继续做别的事情
//任务结束.
//结果：AAAAAAAAAA