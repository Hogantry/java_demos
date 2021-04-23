package com.dfz.serializable;

import java.util.Objects;

/**
 * @version V1.0
 * @author: DFZ
 * @description: test hashcode
 * @date: 2021/3/11 13:33
 * @Copyright: 2021 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class Person {

    private int age;
    private String name;

    public Person() {
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return age == person.age;
    }

//    @Override
//    public int hashCode() {
//        return age;
//    }
}
