package com.dfz.proxy.cglibproxy;

import com.dfz.proxy.service.UserManager;
import com.dfz.proxy.service.impl.UserManagerImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @version V1.0
 * @author: DFZ
 * @description:
 * @date: 2021/4/8 10:07
 * @Copyright: 2021 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class CglibProxy implements MethodInterceptor {

    private Object target;

    public static void main(String[] args) {
        CglibProxy cglib = new CglibProxy();
        UserManager user =  (UserManager) cglib.getCglibProxy(new UserManagerImpl());
        user.delUser("admin");
    }

    /***
     *
     * @param o           生成的代理对象
     * @param method      父类中的方法
     * @param objects     方法参数
     * @param methodProxy 生成的代理对象中的方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Cglib动态代理，监听开始！");
//        Object invoke = method.invoke(target, objects);
        // 上下两个方法执行结果一致
//        Object invoke = methodProxy.invokeSuper(o, objects);
        Object invoke = methodProxy.invoke(target, objects);
        System.out.println("Cglib动态代理，监听结束！");
        return invoke;
    }

    public Object getCglibProxy(Object objectTarget){
        this.target = objectTarget;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(objectTarget.getClass());
        enhancer.setCallback(this);
        Object result = enhancer.create();
        return result;
    }
}
