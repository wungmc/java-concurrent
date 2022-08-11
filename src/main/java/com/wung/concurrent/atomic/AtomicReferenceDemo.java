package com.wung.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子更新引用类型。
 *
 * @author wangmc
 * @date 2022-08-11
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) {

        AtomicReference<User> ar = new AtomicReference<>();
        User user1 = new User("张三", 18);
        ar.set(user1);
        System.out.println(ar.get().getName() + ", " + ar.get().getAge());

        User user2 = new User("李四", 23);
        ar.compareAndSet(user1, user2);
        System.out.println(ar.get().getName() + ", " + ar.get().getAge());

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
// 张三, 18
// 李四, 23
