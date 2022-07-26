package com.wung.concurrent.util;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 迭代。
 * 该迭代不会抛出 ConcurrentModificationException 异常，因为迭代器创建后，就会得到一个 final 型的数组快照，
 * 迭代都是在该快照上进行，对集合的修改都是在新的副本上进行，不会影响该快照。
 *
 * @author wangmc
 * @date 2022-07-26
 */
public class CopyOnWriteArrayListIterateDemo {

    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        // 初始化集合
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(7);

        // 创建迭代器
        Iterator<Integer> iterator = list.iterator();

        // 创建完后，紧接着修改集合
        list.add(9);

        // 继续迭代，并不会迭代出 9 这个元素，也不会抛 ConcurrentModificationException
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}

// out
//1
//3
//5
//7
