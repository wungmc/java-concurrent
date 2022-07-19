/*
 * Copyright (C), 2011-2019.
 */
package com.wung.concurrent.thread.threadpool;

import java.util.concurrent.Callable;

/**
 * @author wung 2019-11-27.
 */
public class MyCallable implements Callable<String> {
	@Override
	public String call() throws Exception {
		// 该方法和 Runnable 不同的一点是：不用捕获异常。
		Thread.sleep(1000L);
		return Thread.currentThread().getName();
	}
	
}
