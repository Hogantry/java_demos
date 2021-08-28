package com.dfz.synchronize;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @author: DFZ
 * @description: synchronized and volatile
 * @date: 2020/6/30 13:48
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class Application {

    private static int a = 0;
    private int b = 0;
    public static final int THREAD_SIZE = 1000;
    public static final CountDownLatch countDownLatch = new CountDownLatch(THREAD_SIZE);
    public static final CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_SIZE);
    public static final Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        Application application = new Application();
        for (int i = 0; i < THREAD_SIZE; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                try {
//                    线程不安全
//                    application.incrb();
//                    Application.incra();
                    Application.incra1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(Application.a);

//        Application application = new Application();
//        for (int i = 0; i < THREAD_SIZE; i++) {
//            new Thread(() -> {
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    application.incrb();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                countDownLatch.countDown();
//            }).start();
//        }
//        countDownLatch.await();
//        System.out.println(application.b);
    }

    public static synchronized int incra() throws InterruptedException {
        TimeUnit.MICROSECONDS.sleep(100);
        return ++a;
    }

    public static int incra1() throws InterruptedException {
        TimeUnit.MICROSECONDS.sleep(1);
        synchronized (object) {
            return ++a;
        }
    }

    private int incrb() throws InterruptedException {
        TimeUnit.MICROSECONDS.sleep(1);
        return ++b;
    }

}
