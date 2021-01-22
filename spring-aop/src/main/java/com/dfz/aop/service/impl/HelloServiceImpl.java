package com.dfz.aop.service.impl;

import com.dfz.aop.service.HelloService;

/**
 * @version V1.0
 * @author: DFZ
 * @description: impl
 * @date: 2020/12/2 11:25
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class HelloServiceImpl implements HelloService {

    public int age;

    @Override
    public Object hello() {
        System.out.println("this is my method~~");
        return "service hello";
    }

    /**
     * 准备一个私有方法，测试用
     */
    public void privateMethod() {
        System.out.println("privateMethod");
    }

    /**
     * 此处用final修饰了  CGLIB也不会代理此方法了
     * @return
     */
    public final int findAge() {
        return age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
