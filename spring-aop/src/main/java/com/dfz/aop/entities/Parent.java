package com.dfz.aop.entities;

/**
 * @version V1.0
 * @author: DFZ
 * @description: parent
 * @date: 2020/12/3 13:16
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class Parent {

    public int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public final int findAge() {
        return age;
    }
}
