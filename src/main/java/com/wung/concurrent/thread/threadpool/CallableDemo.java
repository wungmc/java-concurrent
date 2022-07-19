/*
 * Copyright (C), 2011-2019.
 */
package com.wung.concurrent.thread.threadpool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 使用 ThreadPoolExecutor 构造方法创建线程池
 *
 * @author wung 2019-11-27.
 */
public class CallableDemo {
	
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
		
		List<Future<String>> futureList = new ArrayList<>();
		Callable<String> work = new MyCallable();
		for (int i = 0; i < 10; i++) {
			// submit 可以提交 Runnable 和 Callable
			// Callable 型的任务提交是使用 submit， Callable 有返回值，Runnable 没有
			Future<String> future = executor.submit(work);
			futureList.add(future);
		}
		
		for (Future<String> future : futureList) {
			try {
				System.out.println(new Date() + ": " + future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		// 终止线程池
		executor.shutdown();
	}
	
}

// out
// Wed Nov 27 15:05:59 CST 2019: pool-1-thread-1
// Wed Nov 27 15:06:00 CST 2019: pool-1-thread-2
// Wed Nov 27 15:06:00 CST 2019: pool-1-thread-3
// Wed Nov 27 15:06:00 CST 2019: pool-1-thread-4
// Wed Nov 27 15:06:00 CST 2019: pool-1-thread-5
// Wed Nov 27 15:06:00 CST 2019: pool-1-thread-4
// Wed Nov 27 15:06:01 CST 2019: pool-1-thread-2
// Wed Nov 27 15:06:01 CST 2019: pool-1-thread-5
// Wed Nov 27 15:06:01 CST 2019: pool-1-thread-1
// Wed Nov 27 15:06:01 CST 2019: pool-1-thread-3
