package com.dfz.bytebuddy.aop;

/**
 * @Author DFZ
 * @Date 2021-08-19 14:28
 * @Description
 */
public class Service2 {

    public static int bar(int value) {
        System.out.println("modified bar: " + value);
        return value;
    }

}
