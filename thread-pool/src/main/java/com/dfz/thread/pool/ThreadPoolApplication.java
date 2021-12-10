package com.dfz.thread.pool;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @author: DFZ
 * @description: threadPool
 * @date: 2020/5/18 10:55
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ThreadPoolApplication {

    private static byte[] bytes;

    public static void main(String[] args) throws InterruptedException, IOException {
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 1,
//                TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), new ThreadPoolExecutor.AbortPolicy());
//        for (int i = 0; i < 1; i++) {
//            final int index = i;
//
//            System.out.println(threadPoolExecutor.getPoolSize() + " " + threadPoolExecutor.getCorePoolSize());
//            threadPoolExecutor.execute(() -> {
//                System.out.println("start ---" + index + "---");
//                try {
//                    int b = 0;
//                    int a = 1 / b;
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("end ---" + index + "---");
//            });
//            System.out.println("shutdown");
//            threadPoolExecutor.shutdown();
//        }

//        testThreadPoolWithSynchronousQueue();

        startThread();

        System.in.read();
    }

    public static void testThreadPoolWithSynchronousQueue() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 1,
                TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 100; i++) {
            System.out.println("index:" + i);
            threadPoolExecutor.execute(() -> {
                try {
                    System.out.println("come in");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolExecutor.shutdown();
    }

    /***
     * java中开启一个线程，如果内部是死循环，则GC不会主动回收，除非用户自己暂停该线程，否则线程内任务执行完，则线程会被回收
     */
    public static void startThread() {
        new Thread(() -> {
//            while (true) {
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("sleep 1s");
//            }
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("sleep 1s");
        }, "DFZ").start();
    }
}
