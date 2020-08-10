package com.dfz.thread.pool;

import java.util.concurrent.ArrayBlockingQueue;
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

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 1,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 1; i++) {
            final int index = i;

            System.out.println(threadPoolExecutor.getPoolSize() + " " + threadPoolExecutor.getCorePoolSize());
            threadPoolExecutor.execute(() -> {
                System.out.println("start ---" + index + "---");
                try {
                    int b = 0;
                    int a = 1 / b;
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end ---" + index + "---");
            });
            System.out.println("shutdown");
            threadPoolExecutor.shutdown();
        }
    }
}
