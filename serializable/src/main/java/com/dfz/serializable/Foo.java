package com.dfz.serializable;

import java.io.Serializable;

/**
 * @ClassName Foo
 * @Description Foo
 * @Author dfz
 * @Date 2019-11-07 09:12
 * @Version 1.0
 **/
public class Foo implements Serializable {

    private String fooName;

    public Foo() {
        System.out.println("Foo...");
    }

    public String getFooName() {
        return fooName;
    }

    public void setFooName(String fooName) {
        this.fooName = fooName;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "fooName='" + fooName + '\'' +
                '}';
    }
}

class Foo1 extends Foo {

    private String foo1Name;

    public Foo1() {
        System.out.println("Foo1...");
    }

    public String getFoo1Name() {
        return foo1Name;
    }

    public void setFoo1Name(String foo1Name) {
        this.foo1Name = foo1Name;
    }

    @Override
    public String toString() {
        return "Foo1{" +
                "foo1Name='" + foo1Name + '\'' +
                '}' + "Foo{" +
                "fooName='" + getFooName() + '\'' +
                '}';
    }
}

class Foo2 extends Foo1 {

    private String foo2Name;

    public Foo2() {
        System.out.println("Foo2...");
    }

    public String getFoo2Name() {
        return foo2Name;
    }

    public void setFoo2Name(String foo2Name) {
        this.foo2Name = foo2Name;
    }

    @Override
    public String toString() {
        return "Foo2{" +
                "foo2Name='" + foo2Name + '\'' +
                '}' + "Foo1{" +
                "foo1Name='" + getFoo1Name() + '\'' +
                '}' + "Foo{" +
                "fooName='" + getFooName() + '\'' +
                '}';
    }
}
