package com.dfz.forkjoinpool;

import java.util.concurrent.ForkJoinPool;

/**
 * @version V1.0
 * @author: DFZ
 * @description: ForkJoinPool
 * @date: 2020/4/21 15:02
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ForkJoinPoolMain {

    public static void main(String[] args) {
        // 内置ForkJoinPool
//        SumTask sumTask = new SumTask(1, 999999);
//        sumTask.fork();
//        System.out.println("result: " + sumTask.join());

        // 指定ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer integer = forkJoinPool.invoke(new SumTask(1, 999999));
        forkJoinPool.shutdown();
        System.out.println(integer);
    }

}
