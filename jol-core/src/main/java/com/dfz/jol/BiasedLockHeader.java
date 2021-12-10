package com.dfz.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @Author DFZ
 * @Date 2021-06-30 20:10
 * @Description -XX:BiasedLockingStartupDelay=0
 *
 * // 关闭延迟开启偏向锁，默认延时4s开启偏向锁，4s后新建的对象才会支持偏向锁，之前创建的对象将直接使用轻量级锁
 * -XX:BiasedLockingStartupDelay=0
 * // 禁止偏向锁，默认偏向锁是禁用状态
 * -XX:-UseBiasedLocking
 * // 启用偏向锁，JDK1.6之后默认是开启偏向锁的，只不过是启动4s后才开启
 * -XX:+UseBiasedLocking
 *
 *  https://blog.csdn.net/qq_41055045/article/details/102679948
 *  https://zhuanlan.zhihu.com/p/337477921
 *  https://github.com/farmerjohngit/myblog/issues/12
 *
 */
public class BiasedLockHeader {

    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

//        // 4s后开启偏向锁
//        Object lock1 = new Object();
//        System.out.println(ClassLayout.parseInstance(lock1).toPrintable());
//        TimeUnit.SECONDS.sleep(6);
//        Object lock2 = new Object();
//        System.out.println(ClassLayout.parseInstance(lock2).toPrintable());
//        // 对象调用hashCode方法后，将会进行撤销偏向锁操作，变成无锁不可偏向状态，并且将hashCode写入对象头中，之后也不能使用偏向锁
//        System.out.println(String.format("%08x", lock2.hashCode()));
//        System.out.println(ClassLayout.parseInstance(lock2).toPrintable());

//        //1.加锁前打印对象头
//        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
//        synchronized (lock) {
//            //2.已发生偏向，打印对象头
//            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
//        }
//        //3.偏向锁释放，打印对象头
//        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
//        TimeUnit.SECONDS.sleep(10);
//        //4.偏向锁释放10s后，打印对象头，偏向锁释放后，对象头的markwork是不会变的
//        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
//
//        // 这里偏向锁已经释放了，在子线程中重新去获取锁，会是什么情况呢？
//        new Thread(() -> {
//            synchronized (lock) {
//                // 第一次获取锁的是主线程，此时主线程还是存活状态，因此锁升级为轻量级锁
//                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
//            }
//        }).start();


        // 如果是第一次加锁的线程已经死亡呢？
        final ArrayList<Integer> integers = new ArrayList<>();
        Object lock3 = new Object();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        Thread thread1 = new Thread(() -> {
            synchronized (lock3) {
                integers.add(0);
                System.out.println(ClassLayout.parseInstance(lock3).toPrintable());
            }
        });
        thread1.start();
        thread1.join();

        TimeUnit.SECONDS.sleep(3);

        System.out.println(thread1.isAlive());

        new Thread(() -> {
            synchronized (lock3) {
                integers.add(1);
                System.out.println(ClassLayout.parseInstance(lock3).toPrintable());
            }
        }).start();

        TimeUnit.MICROSECONDS.sleep(100);
        integers.stream().forEach(System.out::println);
        TimeUnit.SECONDS.sleep(10);

    }

}
