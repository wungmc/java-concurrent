package com.wung.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author wangmc
 * @date 2022-08-11
 */
public class AtomicIntegerArrayDemo {

    public static void main(String[] args) {
        int[] value = {1, 3};
        AtomicIntegerArray integerArray = new AtomicIntegerArray(value);
        System.out.println(integerArray.get(1));
        integerArray.set(1, 5);
        System.out.println(integerArray.get(1));
        // AtomicIntegerArray 内部会 clone 一份传进来的数组，所以对其修改，并不会影响原数组
        System.out.println(value[1]);
        System.out.println(integerArray.getAndIncrement(0));
        System.out.println(integerArray.get(0));

    }
}

// out
// 3
// 5
// 3
// 1
// 2