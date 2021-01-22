package com.dfz.aop;

import com.dfz.aop.service.HelloService;
import com.dfz.aop.service.impl.HelloServiceImpl;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @version V1.0
 * @author: DFZ
 * @description: aop 中的TargetSource有何意义
 * @date: 2020/12/2 11:20
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class AopMain {

    public static void main(String[] args) {

        testCglib();

//        HelloService helloService = getProxy(new HelloServiceImpl());
//        System.out.println("------------------------");
//        System.out.println(helloService.getClass());
//        System.out.println(helloService.equals(new Object()));
//        System.out.println(helloService.hashCode());
//        // 只有toString方法会被AOP代理
//        System.out.println(helloService.toString());
//        System.out.println("------------------------");
//        if (helloService instanceof Advised) {
//            Advised advised = (Advised) helloService;
//            System.out.println(advised.getTargetSource());
//            System.out.println(advised.getTargetClass());
//        }
//        //===============演示AopUtils==================
//
//        // AopUtils.isAopProxy:是否是代理对象
//        System.out.println(AopUtils.isAopProxy(helloService));
//        System.out.println(AopUtils.isJdkDynamicProxy(helloService));
//        System.out.println(AopUtils.isCglibProxy(helloService));
//
//        // 拿到目标对象
//        System.out.println(AopUtils.getTargetClass(helloService));
//
//        // selectInvocableMethod:方法@since 4.3  底层依赖于方法MethodIntrospector.selectInvocableMethod
//        // 只是在他技术上做了一个判断： 必须是被代理的方法才行（targetType是SpringProxy的子类,且是private这种方法，且不是static的就不行）
//        // Spring MVC的detectHandlerMethods对此方法有大量调用~~~~~
//        Method method = ClassUtils.getMethod(HelloServiceImpl.class, "hello");
//        System.out.println(AopUtils.selectInvocableMethod(method, HelloServiceImpl.class));
//
//        // 是否是equals方法
//        // isToStringMethod、isHashCodeMethod、isFinalizeMethod  都是类似的
//        System.out.println(AopUtils.isEqualsMethod(method));
//
//        // 它是对ClassUtils.getMostSpecificMethod,增加了对代理对象的特殊处理。。。
//        System.out.println(AopUtils.getMostSpecificMethod(method, HelloService.class));
//
//        // 注意：此处的入参必须是一个Advised：也就是被代理过的对象，否则返回null
//        // 里面的TargetSource必须是SingletonTargetSource才会有所返回
//        //@since 4.3.8
//        System.out.println(AopProxyUtils.getSingletonTarget(helloService));
//        // 获取一个代理对象的最终对象类型
//        System.out.println(AopProxyUtils.ultimateTargetClass(helloService));
    }

    private static HelloService getProxy(Object targetObj) {
        ProxyFactory proxyFactory = new ProxyFactory();
        // 不在构造函数中指定target，而是通过setTarget方式，默认会走CGLIB方式生成代理
        proxyFactory.setTarget(targetObj);
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) -> {
            System.out.println(method.getName() + ": 方法之前执行了~~~");
//            if ("hello".equals(method.getName())) {
//                System.out.println("方法之前执行了~~~");
//            }
        });

        HelloService helloService = (HelloService) proxyFactory.getProxy();
        helloService.hello();
        HelloServiceImpl helloService1 = (HelloServiceImpl) helloService;
        // 注意：这里findAge方法是final的，CGLIB无法生成代理，诡异的是如下方法findAge无法获取到对象的public字段
        // 推测是与Spring AOP生成代理时使用了TargetSource有关，关于该类后续再分析。
        System.out.println("1---" + helloService1.age);
        helloService1.setAge(20);
        System.out.println("2---" + helloService1.age);
        helloService1.getAge();
        System.out.println("3---" + helloService1.getAge());
        helloService1.findAge();
        System.out.println("4---" + helloService1.age);
        System.out.println(helloService.getClass().getName());
        return helloService;
    }

    public static void testCglib() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloServiceImpl.class); // 注意此处的类型必须是实体类
        enhancer.setCallback(new MyMethodInterceptor());

        HelloServiceImpl helloService = (HelloServiceImpl) enhancer.create();
        helloService.hello();

        System.out.println("1---" + helloService.age);
        helloService.setAge(20);
        System.out.println("2---" + helloService.age);
        helloService.getAge();
        System.out.println("3---" + helloService.getAge());
        helloService.findAge();
        System.out.println("4---" + helloService.age);
        System.out.println(helloService.getClass().getName());
    }

    static class MyMethodInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object intercept = methodProxy.invokeSuper(object, args); // 注意这里调用的是methodProxy.invokeSuper
            System.out.println("中介：该房源已发布！");
            return intercept;
        }
    }

}
