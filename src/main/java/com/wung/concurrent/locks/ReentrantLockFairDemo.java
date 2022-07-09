/*
 * Copyright (C), 2011-2020.
 */
package com.wung.concurrent.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 实现公平锁。
 *
 * 启动5个线程，每个线程都获取释放锁两次，中间 sleep 10 毫秒，
 * 从结果来看，线程两次获取锁的顺序是一样的，都是0，1，2，3，4
 *
 * @author wung 2020-03-06.
 */
public class ReentrantLockFairDemo {

	/** ture 表示公平锁，默认非公平锁 */
	private static final Lock LOCK = new ReentrantLock(true);
	
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new Thread(new Test("线程" + i)).start();
		}
	}
	
	static class Test implements Runnable {
		private String name;
		public Test(String name) {
			this.name = name;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < 2; i++) {
				LOCK.lock();
				try {
					System.out.println(name + " 获取锁成功！");
					TimeUnit.MICROSECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					LOCK.unlock();
				}
			}
		}
	}
}

// 输出
// 线程0 获取锁成功！
// 线程1 获取锁成功！
// 线程2 获取锁成功！
// 线程3 获取锁成功！
// 线程4 获取锁成功！
// 线程0 获取锁成功！
// 线程1 获取锁成功！
// 线程2 获取锁成功！
// 线程3 获取锁成功！
// 线程4 获取锁成功！