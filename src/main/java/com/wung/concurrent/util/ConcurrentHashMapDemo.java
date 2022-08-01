package com.wung.concurrent.util;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangmc
 * @date 2022-08-01
 */
public class ConcurrentHashMapDemo {

    private static final ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            map.put(i, i + "");
        }


        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

            map.put(100, "100");
            System.out.println("add:" + 100);
        });

        Thread t2 = new Thread(() -> {
            Set<Map.Entry<Integer, String>> entries = map.entrySet();
            for (Map.Entry<Integer, String> entry : entries) {
                System.out.println(entry.getKey() + " --- " + entry.getValue());

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();


    }


}

// out
//0 --- 0
//add:100
//1 --- 1
//2 --- 2
//3 --- 3
//4 --- 4
//100 --- 100  // 100可以迭代出来

