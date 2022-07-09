/*
 * Copyright (C), 2011-2020.
 */
package com.wung.concurrent.locks;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 虽然 ReentrantLock 和 Synchronized 简单实用，但是行为上有一定局限性，要么不占，要么独占。
 * 实际应用场景中，有时候不需要大量竞争的写操作，而是以并发读取为主，为了进一步优化并发操作的粒度，Java 提供了读写锁。
 *
 * 读写锁基于的原理是多个读操作不需要互斥，
 * 如果读锁试图锁定时，写锁是被某个线程持有，读锁将无法获得，而只好等待对方操作结束，
 * 这样就可以自动保证不会读取到有争议的数据。
 *
 * ReadWriteLock 代表了一对锁，下面是一个基于读写锁实现的数据结构，
 * 当数据量较大，并发读多、并发写少的时候，能够比纯同步版本凸显出优势.
 *
 * @author wung 2020-03-12.
 */
public class ReentrantReadWriteLockDemo {
	private final Map<String, String> map = new TreeMap<>();
	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock read = readWriteLock.readLock();
	private final Lock write = readWriteLock.writeLock();
	
	public String get(String key) {
		read.lock();
		try {
			System.out.println("读锁锁定");
			return map.get(key);
		} finally {
			read.unlock();
			System.out.println("读锁释放");
		}
	}
	
	public String put(String key, String value) {
		write.lock();
		try {
			System.out.println("写锁锁定");
			return map.put(key, value);
		} finally {
			write.unlock();
			System.out.println("写锁释放");
		}
	}
	
	public static void main(String[] args) {
		ReentrantReadWriteLockDemo readWriteLockDemo = new ReentrantReadWriteLockDemo();
		readWriteLockDemo.put("a", "jack");
		readWriteLockDemo.get("a");
	}
}
