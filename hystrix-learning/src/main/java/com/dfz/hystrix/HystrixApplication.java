package com.dfz.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.properties.HystrixDynamicProperties;
import com.netflix.hystrix.strategy.properties.HystrixDynamicProperty;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import rx.Observable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @author: DFZ
 * @description:
 * @date: 2020/10/19 10:06
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class HystrixApplication {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        testCommandHelloWorld();
        testProperties();
    }

    public static void testCommandHelloWorld() throws ExecutionException, InterruptedException {
        // 三种执行方式：

        // 1、普通方式
        String s = new CommandHelloWorld("Bob").execute();
        // Hello Bob!------------
        System.out.println(s);

        String fallbackValue = new CommandHelloWorld(null).execute();
        // 说明：若你没有提供fallback函数，那结果是：
        // com.netflix.hystrix.exception.HystrixRuntimeException: CommandHelloWorld failed and no fallback available.
        // "this is fallback msg"
        System.out.println(fallbackValue);

        // 2、异步方式。什么时候需要时候什么时候get
        Future<String> s1 = new CommandHelloWorld("Bob").queue();
        // Hello Bob!
        System.out.println(s1.get());

        // 3、RxJava方式。吞吐量更高，但对程序员的要求更高
        Observable<String> s2 = new CommandHelloWorld("Bob").observe();
        // Hello Bob!
        s2.subscribe(System.out::println);
    }

    public static void testProperties() throws InterruptedException {
        HystrixPlugins instance = HystrixPlugins.getInstance();
        HystrixCommandProperties abc = instance.getPropertiesStrategy().getCommandProperties(HystrixCommandKey.Factory.asKey("abc"),
                HystrixCommandProperties.Setter());
        HystrixProperty<String> stringHystrixProperty = abc.executionIsolationThreadPoolKeyOverride();
        stringHystrixProperty.get();
        HystrixDynamicProperties dynamicProperties = instance.getDynamicProperties();

        System.out.println(dynamicProperties.getClass());

        // dynamicProperties.getString()
        HystrixDynamicProperty<String> nameProperty = HystrixDynamicProperties.
                Util.getProperty(dynamicProperties, "name", "defaultValue", String.class);
        nameProperty.addCallback(() -> {
            // 属性名
            String name = nameProperty.getName();
            System.out.println("属性" + name + "发生了变更");
        });
        System.out.println(nameProperty.get());

        // hold住主线程
        while (true) {
            // 因为poll轮询默认是60秒check一次
            TimeUnit.SECONDS.sleep(60);
            System.out.println(nameProperty.get());
        }
    }

}
