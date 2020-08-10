package com.dfz.java.spi.impl;

import com.dfz.java.spi.Robot;

/**
 * @ClassName OptimusPrime
 * @Description OptimusPrime
 * @Author dfz
 * @Date 2019-09-25 16:09
 * @Version 1.0
 **/
public class OptimusPrime implements Robot {
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}
