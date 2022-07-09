package com.wung.concurrent.thread;

/**
 *
 * Created by wung on 2016/12/4.
 */
public class MyThread extends Thread {
    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
		for (int i = 0; i < 10; i++) {
            System.out.println(name + ", i = " + i);
        }
    }
}

