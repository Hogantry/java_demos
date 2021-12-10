package com.dfz.bytebuddy.aop;

/**
 * @Author DFZ
 * @Date 2021-08-18 18:16
 * @Description
 */
public class Service {

    @Log
    public int foo(int value) {
        System.out.println("foo: " + value);
        return value;
    }

    public int bar(int value) {
        System.out.println("bar: " + value);
        return value;
    }

}
