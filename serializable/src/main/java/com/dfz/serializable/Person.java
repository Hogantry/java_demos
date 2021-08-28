package com.dfz.serializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @version V1.0
 * @author: DFZ
 * @description: test hashcode
 * @date: 2021/3/11 13:33
 * @Copyright: 2021 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class Person implements Serializable {
    private static final long serialVersionUID = 1782600672465264753L;

    private int age;
    private transient String name;

    public Person() {
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    @Override
    public int hashCode() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        // 默认序列化时使用的方法，此时不会序列化name字段
        out.defaultWriteObject();
        // 自定义
        out.writeObject(new StringBuffer(name).reverse());
    }

    private void readObject(ObjectInputStream ins) throws IOException,ClassNotFoundException{
        ins.defaultReadObject();
        name = ((StringBuffer)ins.readObject()).reverse().toString();
    }
}
