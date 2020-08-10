package com.dfz.java.spi;

import java.util.ServiceLoader;

/**
 * @ClassName SpiMain
 * @Description SpiMain
 * @Author dfz
 * @Date 2019-09-25 16:05
 * @Version 1.0
 **/
public class SpiMain {

    public static void main(String[] args) {
        ServiceLoader<Robot> load = ServiceLoader.load(Robot.class);
        for (Robot robot : load) {
            robot.sayHello();
        }
    }

}
