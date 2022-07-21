package com.wung.concurrent.thread.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 自定义对long型数组求和的分支／合并计算框架。
 * 1、是否可在分的判断
 * 2、计算
 *
 * @author wung
 * @date 2022-07-21
 */
public class ForkJoinSum extends RecursiveTask<Long> {
    // 因为任务有返回值，所以需要继承 RecursiveTask 类型

    private final long[] nums;
    private final int start;
    private final int end;

    public ForkJoinSum(long[] nums) {
        this.nums = nums;
        this.start = 0;
        this.end = nums.length;
    }

    private ForkJoinSum(long[] nums, int start, int end) {
        this.nums = nums;
        this.start = start;
        this.end = end;
    }

    /**
     * 重新 compute 方法，做两件事：任务分割和任务计算
     *
     * @return
     */
    @Override
    protected Long compute() {
        int length = end - start;
        // 最小单位为 10000
        if (length <= 10000) {
            // 如果不可再分了，则计算
            return seqSum();
        }

        /**
         * 任务拆分：1分2，2分4，。。。
         */
        System.out.println("开始任务拆分，当前 " + Thread.currentThread().getName() + " : " + length);
        ForkJoinSum leftTask = new ForkJoinSum(nums, start, start + length/2);
        // 左任务异步执行
        leftTask.fork();
        ForkJoinSum rightTask = new ForkJoinSum(nums, start + length/2, end);
        // 右任务同步执行
        Long right = rightTask.compute();
        // 获取left的计算结果，如果任务没有结束，则等待
        Long left = leftTask.join();
        return left + right;
    }


    private long seqSum() {
        long sum = 0L;
        for (int i = start; i < end; i++) {
            sum += nums[i];
        }
        return sum;
    }

    public static Long forkJoinSum(long n) {
        long[] nums = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> forkJoinSum = new ForkJoinSum(nums);
        return new ForkJoinPool().invoke(forkJoinSum);
    }

    public static Long forkJoinSum2(long[] nums) {
        ForkJoinTask<Long> forkJoinSum = new ForkJoinSum(nums);
        return new ForkJoinPool().invoke(forkJoinSum);
    }

    public static void main(String[] args) {
        Long result = forkJoinSum(100000);
        System.out.println(result);
    }

}

// out
//开始任务拆分，当前 ForkJoinPool-1-worker-1 : 100000
//开始任务拆分，当前 ForkJoinPool-1-worker-1 : 50000
//开始任务拆分，当前 ForkJoinPool-1-worker-1 : 25000
//开始任务拆分，当前 ForkJoinPool-1-worker-2 : 50000
//开始任务拆分，当前 ForkJoinPool-1-worker-3 : 25000
//开始任务拆分，当前 ForkJoinPool-1-worker-4 : 25000
//开始任务拆分，当前 ForkJoinPool-1-worker-5 : 12500
//开始任务拆分，当前 ForkJoinPool-1-worker-4 : 12500
//开始任务拆分，当前 ForkJoinPool-1-worker-1 : 12500
//开始任务拆分，当前 ForkJoinPool-1-worker-6 : 12500
//开始任务拆分，当前 ForkJoinPool-1-worker-2 : 25000
//开始任务拆分，当前 ForkJoinPool-1-worker-2 : 12500
//开始任务拆分，当前 ForkJoinPool-1-worker-4 : 12500
//开始任务拆分，当前 ForkJoinPool-1-worker-2 : 12500
//开始任务拆分，当前 ForkJoinPool-1-worker-3 : 12500
//5000050000
