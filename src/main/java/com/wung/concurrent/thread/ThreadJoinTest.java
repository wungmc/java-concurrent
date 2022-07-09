/*
 * Copyright (C), 2011-2020.
 */
package com.wung.concurrent.thread;

/**
 * Thread.join 方法测试。
 *
 * join的意思是：阻塞当前线程的执行，让调用join方法的线程执行完成。
 *
 * @author wung 2020-03-09.
 */
public class ThreadJoinTest {
	
	public static void main(String[] args) throws InterruptedException {
		MyThread t1 = new MyThread("a");
		MyThread t2 = new MyThread("b");
		
		t1.start();
		
		// 阻塞当前线程（这里为主线程），直到 t1 执行完毕。
		t1.join();
		
		t2.start();
	}
}
