package com.dfz.async.component;

import com.dfz.async.annotation.DfzAsync;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @author: DFZ
 * @description: async bean
 * @date: 2020/8/11 13:31
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class AsyncBean {

    /**
     * 方法加了@Async注解之后，返回值类型只能为{@link CompletableFuture}、{@link ListenableFuture}、{@link Future}或者void，除此之外，
     * 全部返回null， 具体可参考{@link AsyncExecutionAspectSupport}类中doSubmit()方法
     * @return
     * @param countDownLatch
     */
    @Async
    public String runAsync(CountDownLatch countDownLatch) {
        System.out.println("start async");
        System.out.println(Thread.currentThread().getName());
        try {
            TimeUnit.MICROSECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        return "hello world";
    }

//    @Async("asyncA")
    @Async
    public String runAsyncA(CountDownLatch countDownLatch) {
        System.out.println("start asyncA");
        System.out.println(Thread.currentThread().getName());
        try {
            TimeUnit.MICROSECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        return "hello world A";
    }

//    @Async("asyncB")
    @Async
    public String runAsyncB(CountDownLatch countDownLatch) {
        System.out.println("start asyncB");
        System.out.println(Thread.currentThread().getName());
        try {
            TimeUnit.MICROSECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        return "hello world B";
    }

    /**
     * 可自定义注解，在@EnableAsync(annotation = DfzAsync.class)中指定，则默认的@Async注解将不起作用，默认会在当前线程运行，注解了
     * 自定义的@DfzAsync的方法会在线程池中运行。
     * @return
     */
    @DfzAsync
    public String runDfzAsync() {
        System.out.println("start dfz async");
        System.out.println(Thread.currentThread().getName());
        try {
            TimeUnit.MICROSECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world B";
    }

}
