package com.wung.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * AtomicReferenceFieldUpdater是基于反射的工具类，用来将指定类型的指定的 volatile 引用字段进行原子更新。
 * 通常一个类的 volatile 成员属性的 setter 和 getter 两个操作是非原子的，若想将其变为原子的，则可通过AtomicReferenceFieldUpdater来实现。
 *
 * 注意，原子更新器的使用存在比较苛刻的条件如下：
 * 1. 操作的字段不能是static类型。
 * 2. 操作的字段不能是final类型的，因为final根本没法修改。
 * 3. 字段必须是volatile修饰的，也就是数据本身是读一致的。
 * 4. 属性必须对当前的Updater所在的区域是可见的，如果不是当前类内部进行原子更新器操作不能使用private，
 *  protected子类操作父类时修饰符必须是protect权限及以上，如果在同一个package下则必须是default权限及以上，也就是说无论何时都应该保证操作类与被操作类间的可见性。
 *
 * @author wangmc
 * @date 2022-08-11
 */
public class AtomicReferenceFieldUpdaterDemo {

    public static void main(String[] args) {
        // 创建一个 User 类的 String 类型的 name 属性的修改器
        AtomicReferenceFieldUpdater<User, User> nameFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(User.class, User.class, "parent");

        User parent = new User(null, 58);
        User parent2 = new User(null, 68);
        User user = new User(parent, 18);
        // 如果 user 对象的 parent 属性是 parent，就将其修改为 parent2
        nameFieldUpdater.compareAndSet(user, parent, parent2);
        System.out.println(user.getParent().getAge());
    }

    static class User {
        // 引用类型的属性
        // 该属性对 Updater 是不可见的，所以这里不能使用 private，
        // 必须使用 volatile 修饰
        volatile User parent;
        private int age;

        public User(User parent, int age) {
            this.parent = parent;
            this.age = age;
        }

        public User getParent() {
            return parent;
        }

        public void setParent(User parent) {
            this.parent = parent;
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
// 68