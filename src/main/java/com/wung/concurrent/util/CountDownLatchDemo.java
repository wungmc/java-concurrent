/*
 * Copyright (C), 2011-2020.
 */
package com.wung.concurrent.util;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch 叫倒计数，允许一个或多个线程等待某些操作完成。
 *
 * 用法：CountDownLatch 构造方法指明计数数量，被等待线程调用 countDown 将计数器减 1，使用 await 进行线程等待。
 *
 * 看一个场景：
 * 跑步比赛，裁判需要等到所有的运动员（“其他线程”）都跑到终点（达到目标），才能去算排名和颁奖。
 *
 *
 * @author wung 2020-03-12.
 */
public class CountDownLatchDemo {
	
	public static void main(String[] args) {
		CountDownLatchDemo demo = new CountDownLatchDemo();
		demo.begin();
	}
	
	/**
	 * 设置4个线程
	 */
	private final CountDownLatch countDownLatch = new CountDownLatch(4);
	
	/**
	 * 运动员类
	 */
	private class Runner implements Runnable {
		// 运动员跑了多少秒
		private int time;
		public Runner(int time) {
			this.time = time;
		}
		
		@Override
		public void run() {
			try {
				// 模拟跑的过程
				Thread.sleep(time * 1000);
				System.out.println(Thread.currentThread().getName() + " 跑完。耗时: " + time + " 秒");
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				// 跑完后，计数器减1
				countDownLatch.countDown();
			}
		}
	}
	
	public void begin() {
		System.out.println("赛跑开始...");
		Random random = new Random(System.currentTimeMillis());
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 4; i++) {
			int time = random.nextInt(3) + 1;
			executorService.submit(new Runner(time));
		}

		try {
			// 等待计数器减为0，即所有线程都运行完
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		System.out.println("所有运动员都跑完了，开始统计成绩...");
	}
}

// out
//赛跑开始...
//pool-1-thread-4 跑完。耗时: 2 秒
//pool-1-thread-3 跑完。耗时: 3 秒
//pool-1-thread-1 跑完。耗时: 3 秒
//pool-1-thread-2 跑完。耗时: 3 秒
//所有运动员都跑完了，开始统计成绩...
