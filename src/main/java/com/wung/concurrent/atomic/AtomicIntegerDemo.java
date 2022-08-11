package com.wung.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子更新整型
 *
 * @author wangmc
 * @date 2022-08-11
 */
public class AtomicIntegerDemo {

    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(1);
        System.out.println(integer.getAndIncrement());
        System.out.println(integer.get());
        System.out.println(integer.incrementAndGet());

    }

}

// out
// 1
// 2
// 3
