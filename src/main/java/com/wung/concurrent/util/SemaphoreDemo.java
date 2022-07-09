/*
 * Copyright (C), 2011-2020.
 */
package com.wung.concurrent.util;

import java.util.concurrent.Semaphore;

/**
 * Semaphore，Java 版本的信号量实现，用于控制同时访问的线程个数，来达到限制通用资源访问的目的，
 * 其原理是通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 *
 * @author wung 2020-03-12.
 */
public class SemaphoreDemo {
	
	/**
	 * 一共有5个许可
	 */
	private final Semaphore semaphore = new Semaphore(5);
	
	public static void main(String[] args) {
		SemaphoreDemo demo = new SemaphoreDemo();
		demo.begin();
	}
	
	public void begin() {
		// 模拟10个线程，去抢5个许可
		for (int i = 0; i < 10; i++) {
			new Thread(new Worker()).start();
		}
	}
	
	private class Worker implements Runnable {
		@Override
		public void run() {
			try {
				// 先获取许可
				semaphore.acquire();
				System.out.println(Thread.currentThread().getName() + " 获取许可");
				// 获取成功后才可以运行
				Thread.sleep(2000);
				// 运行结束，释放许可
				semaphore.release();
				System.out.println(Thread.currentThread().getName() + " 释放许可");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


// out
// Thread-0 获取许可
// Thread-1 获取许可
// Thread-2 获取许可
// Thread-3 获取许可
// Thread-4 获取许可
// Thread-0 释放许可
// Thread-5 获取许可
// Thread-1 释放许可
// Thread-6 获取许可
// Thread-3 释放许可
// Thread-8 获取许可
// Thread-4 释放许可
// Thread-2 释放许可
// Thread-9 获取许可
// Thread-7 获取许可
// Thread-5 释放许可
// Thread-6 释放许可
// Thread-8 释放许可
// Thread-9 释放许可
// Thread-7 释放许可

// 从输出结果可以看出：前5个线程首先获取到许可开始运行，其他5个线程阻塞；
// 等到0释放了许可，线程5立即获取到许可开始运行。。。
// 总之，大家必须先拿到许可，才能运行，否则就阻塞。