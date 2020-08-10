package com.dfz.java.spi.impl;

import com.dfz.java.spi.Robot;

/**
 * @ClassName Bumblebee
 * @Description Bumblebee
 * @Author dfz
 * @Date 2019-09-25 16:10
 * @Version 1.0
 **/
public class Bumblebee implements Robot {

    public Bumblebee() throws Exception {
        throw new Exception("abc");
    }

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}
