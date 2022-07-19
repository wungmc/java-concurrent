/*
 * Copyright (C), 2011-2019.
 */
package com.wung.concurrent.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用 ThreadPoolExecutor 构造方法创建线程池
 *
 * @author wung 2019-11-27.
 */
public class ThreadPoolExecutorDemo {
	
	private static final int CORE_POOL_SIZE = 5;
	private static final int MAXIMUM_POOL_SIZE = 10;
	private static final int QUEUE_CAPACITY = 100;
	private static final long KEEP_ALIVE_TIME = 1L;
	
	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE,
				MAXIMUM_POOL_SIZE,
				KEEP_ALIVE_TIME,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY),
				new ThreadPoolExecutor.CallerRunsPolicy());
		
		for (int i = 0; i < 10; i++) {
			Runnable work = new MyRunnable(i + "");
			// execute 只能提交 Runnable 任务
			executor.execute(work);
		}
		
		// 终止线程池
		executor.shutdown();
		while (!executor.isTerminated()) {
		
		}
		
		System.out.println("All threads finished");
	}
	
}

// out
// pool-1-thread-5 Start : Wed Nov 27 14:45:02 CST 2019
// pool-1-thread-1 Start : Wed Nov 27 14:45:02 CST 2019
// pool-1-thread-3 Start : Wed Nov 27 14:45:02 CST 2019
// pool-1-thread-2 Start : Wed Nov 27 14:45:02 CST 2019
// pool-1-thread-4 Start : Wed Nov 27 14:45:02 CST 2019
// pool-1-thread-5 End : Wed Nov 27 14:45:07 CST 2019
// pool-1-thread-3 End : Wed Nov 27 14:45:07 CST 2019
// pool-1-thread-3 Start : Wed Nov 27 14:45:07 CST 2019
// pool-1-thread-5 Start : Wed Nov 27 14:45:07 CST 2019
// pool-1-thread-1 End : Wed Nov 27 14:45:07 CST 2019
// pool-1-thread-2 End : Wed Nov 27 14:45:07 CST 2019
// pool-1-thread-1 Start : Wed Nov 27 14:45:07 CST 2019
// pool-1-thread-2 Start : Wed Nov 27 14:45:07 CST 2019
// pool-1-thread-4 End : Wed Nov 27 14:45:07 CST 2019
// pool-1-thread-4 Start : Wed Nov 27 14:45:07 CST 2019
// pool-1-thread-3 End : Wed Nov 27 14:45:12 CST 2019
// pool-1-thread-1 End : Wed Nov 27 14:45:12 CST 2019
// pool-1-thread-5 End : Wed Nov 27 14:45:12 CST 2019
// pool-1-thread-2 End : Wed Nov 27 14:45:12 CST 2019
// pool-1-thread-4 End : Wed Nov 27 14:45:12 CST 2019
// All threads finished

// 可以看到池中只创建了 5 个线程，循环执行了10个任务。