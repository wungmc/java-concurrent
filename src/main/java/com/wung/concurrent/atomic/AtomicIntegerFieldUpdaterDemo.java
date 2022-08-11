package com.wung.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 原子更新属性(字段)
 *
 * @author wangmc
 * @date 2022-08-11
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) {

        AtomicIntegerFieldUpdater fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
        User user = new User("张三", 18);
        System.out.println(fieldUpdater.getAndIncrement(user));
        System.out.println(fieldUpdater.get(user));

    }

    static class User {
        volatile String name;
        volatile int age;

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
// 18
// 19
