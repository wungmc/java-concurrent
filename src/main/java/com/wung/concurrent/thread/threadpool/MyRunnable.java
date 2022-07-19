/*
 * Copyright (C), 2011-2019.
 */
package com.wung.concurrent.thread.threadpool;

import java.util.Date;

/**
 * 模拟一个线程任务
 *
 * @author wung 2019-11-27.
 */
public class MyRunnable implements Runnable {
	
	private String command;
	
	public MyRunnable(String command) {
		this.command = command;
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " Start : " + new Date());
		work();
		System.out.println(Thread.currentThread().getName() + " End : " + new Date());
	}
	
	private void work() {
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
