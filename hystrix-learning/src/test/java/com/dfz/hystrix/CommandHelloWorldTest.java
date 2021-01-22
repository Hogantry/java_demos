package com.dfz.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixContextRunnable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class CommandHelloWorldTest {

    private static final HystrixRequestVariableDefault<String> NAME_VARIABLE = new HystrixRequestVariableDefault<>();

    @Test
    public void fun3() throws InterruptedException {
        HystrixRequestContext.initializeContext();
        HystrixRequestContext mainContext = HystrixRequestContext.getContextForCurrentThread();
        // 设置变量：让其支持传递到子线程 or 线程池
        NAME_VARIABLE.set("YoutBatman");

        // 子线程的Runnable任务，必须使用`HystrixContextRunnable`才能得到上面设置的值哦
        new Thread(new HystrixContextRunnable(() -> {
            HystrixRequestContext contextForCurrentThread = HystrixRequestContext.getContextForCurrentThread();
            System.out.println(contextForCurrentThread == mainContext);
            System.out.println("当前线程绑定的变量值是：" + NAME_VARIABLE.get());
        })).start();

        TimeUnit.SECONDS.sleep(1);
        HystrixRequestContext.getContextForCurrentThread().close();

    }


}