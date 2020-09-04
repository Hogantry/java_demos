package com.dfz.system.property;

import java.util.*;

/**
 * @ClassName PropertyMain
 * @Description 运行可执行jar时，如果需要给jar中的main方法传递参数，在命令后面跟上参数即可，多个参数之间使用空格分隔。
 *              例如：java -jar Main.jar 1 2 3 4，这里参数即是程序的Program arguments
 *
 *              命令后使用-Dname=value方式可以设置jar包运行时的VM options参数，在程序中可使用System.getProperty(name)方式获取值
 *
 *              System.getenv(name)可获取系统环境变量的值，在IDEA中可配置Environment variables，设置自定义环境变量
 *
 *              --server.port=8080 类似的参数是Springboot特别支持的启动参数类型，具体原理还是main方法的参数，只是Springboot
 *              对它做了解析，如果未解析，则整个字符串都作为main方法的一个字符串参数
 *
 * @Author dfz
 * @Date 2019-07-22 17:01
 * @Version 1.0
 **/
public class PropertyMain {

    public static void main(String[] args) {

        Arrays.stream(args).forEach(System.out::println);

        // 系统运行时传递的启动参数
        String dfz1 = System.getProperty("DFZ1");
        String dfz2 = System.getProperty("DFZ2");
        // -DDFZ4 只配置了key，则value为空字符串，否则value为null
        String dfz4 = System.getProperty("DFZ4");
        String dfz5 = System.getProperty("DFZ5");
        System.out.println(dfz4 + ";" + dfz5);
        // 环境变量
        String dfz3 = System.getenv("DFZ3");
        System.out.println("DFZ1: " + dfz1 + ";DFZ2: " + dfz2 + ";DFZ3: " + dfz3);
        ArrayList<Integer> integers = new ArrayList<>(0);
        integers.add(1);
        System.out.println("-----------env-------------");
        Properties properties = System.getProperties();
        properties.forEach((k,v)->System.out.println(k + ":" + v));
        System.out.println("-----------systemEnv-------------");
        Map<String, String> systemEnv = System.getenv();
        systemEnv.forEach((k, v) -> System.out.println(k + ":" + v));
    }

}
