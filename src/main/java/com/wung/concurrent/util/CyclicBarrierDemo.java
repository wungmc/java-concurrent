/*
 * Copyright (C), 2011-2020.
 */
package com.wung.concurrent.util;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 叫循环栅栏，它实现让一组线程等待至某个状态之后再全部同时执行，而且当所有等待线程被释放后，
 * CyclicBarrier 可以被重复使用。
 * CyclicBarrier 的典型应用场景是用来等待并发线程结束。
 *
 * CyclicBarrier 的主要方法是 await()，await() 每被调用一次，计数便会减少 1，并阻塞住当前线程。当计数减至 0 时，阻塞解除，
 * 所有在此 CyclicBarrier 上面阻塞的线程开始运行。
 *
 * 在这之后，如果再次调用 await()，计数就又会变成 N-1，新一轮重新开始，这便是 Cyclic 的含义所在。
 * CyclicBarrier.await() 带有返回值，用来表示当前线程是第几个到达这个 Barrier 的线程。
 *
 * 应用场景：模拟并发，我需要启动 100 个线程去同时访问某一个地址，我希望它们能同时并发，而不是一个一个的去执行。
 * 再比如，10个人一桌吃饭，先到的人要等待其他人，直到10个人到齐后才开饭。
 *
 * @author wung 2020-03-12.
 */
public class CyclicBarrierDemo {
	
	/**
	 * 设置5个计数器（5个线程）
	 */
	private final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
	
	public static void main(String[] args) {
		CyclicBarrierDemo cyclicBarrierDemo = new CyclicBarrierDemo();
		cyclicBarrierDemo.begin();
	}
	
	public void begin() {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runner(i)).start();
		}
	}
	
	private class Runner implements Runnable {
		// 线程编号
		private int num;
		
		public Runner(int num) {
			this.num = num;
		}
		
		@Override
		public void run() {
			Random random = new Random(System.currentTimeMillis());
			try {
				// 模拟线程的前期准备时间
				int time = random.nextInt(3) + 1;
				Thread.sleep(time * 1000);
				// 线程进入阻塞，当计数器减为0时，开始运行
				System.out.println(Thread.currentThread().getName() + "进入阻塞");
				cyclicBarrier.await();
			} catch (InterruptedException e) {
				// await 可响应中断
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} catch (BrokenBarrierException e) {
				// 抛出该异常时，说明 CyclicBarrier 的条件被破坏，已经无法等待所有线程都到齐了（比如有线程被中断了）
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "开始运行");
		}
	}
}

// out
// Thread-4进入阻塞
// Thread-3进入阻塞
// Thread-0进入阻塞
// Thread-1进入阻塞
// Thread-2进入阻塞
// Thread-2开始运行
// Thread-3开始运行
// Thread-0开始运行
// Thread-4开始运行
// Thread-1开始运行
