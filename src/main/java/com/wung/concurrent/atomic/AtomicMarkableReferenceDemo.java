package com.wung.concurrent.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 为了解决 ABA 问题.
 * 使用一个 boolean 类型的来标记值是否被修改过
 *
 * @author wangmc
 * @date 2022-08-12
 */
public class AtomicMarkableReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        // 引用类型
        User user = new User("张三", 18);
        // 构建一个 AtomicMarkableReference 对象，引用类型数据初始值是 user，标记位初始是 false
        AtomicMarkableReference<User> amr = new AtomicMarkableReference<>(user, false);
        System.out.println(amr.isMarked());


        Thread t1 = new Thread(() -> {
            User user1 = new User("李四", 28);
            // 如果原引用值是 user、标记是 false， 则将引用修改为 user1、标记修改为 true
            amr.compareAndSet(amr.getReference(), user1, false, true);
            System.out.println(amr.isMarked());
        });

        Thread t2 = new Thread(() -> {
            User user2 = new User("王五", 38);
            // 如果原引用值是 user1、标记是 false， 则将引用修改为 user2、标记修改为 false
            amr.compareAndSet(amr.getReference(), user2, false, false);
            System.out.println(amr.isMarked());
        });

        t1.start();
        t1.join();

        // 由于 t1 先修改了标记位，所以 t2 会修改失败(标记仍然是 true)
        t2.start();
        t2.join();

    }

    static class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}

// out
// false
// true
// true