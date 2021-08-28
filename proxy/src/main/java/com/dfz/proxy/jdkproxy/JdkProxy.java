package com.dfz.proxy.jdkproxy;

import com.dfz.proxy.service.UserManager;
import com.dfz.proxy.service.impl.UserManagerImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @version V1.0
 * @author: DFZ
 * @description: jdk proxy
 * @date: 2021/4/8 09:34
 * @Copyright: 2021 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class JdkProxy implements InvocationHandler {

    private Object target;

    public static void main(String[] args) {
        // 1、生成$Proxy0的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        JdkProxy jdkProxy = new JdkProxy();
        UserManager user = (UserManager) jdkProxy.getJDKProxy(new UserManagerImpl());
        // 直接调用代理类的toString方法，
//        System.out.println(user);
        user.addUser("admin", "123123");
    }

    /***
     *
     * @param proxy  生成的代理类
     * @param method 接口中的方法对象
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 这里的proxy就是代理对象
        // 这里的method是接口类的Method对象

        // 这里打印proxy，则调用toString方法，会产生死循环，在invoke方法中调用proxy任意方法，都得注意死循环的问题
//        System.out.println("proxy:" + proxy);
        System.out.println("proxy:" + proxy.getClass().getName());
        System.out.println("method: " + method.getDeclaringClass().getName());
        System.out.println("JDK动态代理，监听开始！");
        Object result = method.invoke(target, args);
        System.out.println("JDK动态代理，监听结束！");
        return result;
    }

    private Object getJDKProxy(Object targetObject) {
        this.target = targetObject;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }
}
