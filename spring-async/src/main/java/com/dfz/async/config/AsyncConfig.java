package com.dfz.async.config;

import com.dfz.async.annotation.DfzAsync;
import com.dfz.async.component.AsyncBean;
import com.dfz.async.component.NormalBean;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @version V1.0
 * @author: DFZ
 * @description: async config
 * @date: 2020/8/11 13:24
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Configuration
@EnableAsync(annotation = DfzAsync.class)
public class AsyncConfig {

    @Bean
    public NormalBean normalBean() {
        return new NormalBean();
    }

    @Bean
    public AsyncBean asyncBean() {
        return new AsyncBean();
    }

    /***
     * @Async 必须提供相应线程池。
     *        默认是从IOC容器中找唯一实现{@link org.springframework.core.task.TaskExecutor}的bean，
     *        或者是实现{@link java.util.concurrent.Executor}且 bean name 为 "taskExecutor"的bean
     *        Springboot在spring-boot-autoconfigure子项目中引入{@link TaskExecutionAutoConfiguration}，配置了默认的线程池
     *
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("default-");
        executor.setCorePoolSize(3);
        executor.setDaemon(true);
        return executor;
    }

    /***
     * @Async("asyncA") 会在IOC容器中找到bean name为asyncA的线程池并使用，找不到会报错。
     *
     * @return
     */
    @Bean("asyncA")
    public ThreadPoolTaskExecutor executorA() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("executorA-");
        executor.setDaemon(true);
        return executor;
    }

    @Bean("asyncB")
    public ThreadPoolTaskExecutor executorB() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("executorB-");
        executor.setDaemon(true);
        return executor;
    }

    /**
     * 也可以通过如下bean，同时提供线程池与错误处理实例
     * @return
     */
//    @Bean
    public AsyncConfigurer asyncConfigurer() {
        return new AsyncConfigurer(){
            @Override
            public Executor getAsyncExecutor() {
                ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
                // ThreadPoolTaskExecutor 在afterPropertiesSet方法中自动初始化了线程池
                // 这里没有定义成bean，不会自动调用初始化，需要手动初始化，如何才能实现自动初始化？
                executor.setThreadNamePrefix("executor-");
                executor.initialize();
                executor.setDaemon(true);
                return executor;
            }

            @Override
            public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
                return new SimpleAsyncUncaughtExceptionHandler();
            }
        };
    }

}
