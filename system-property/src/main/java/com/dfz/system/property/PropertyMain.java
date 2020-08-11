package com.dfz.system.property;

import java.util.*;

/**
 * @ClassName PropertyMain
 * @Description 运行可执行jar时，如果需要给jar中的main方法传递参数，在命令后面跟上参数即可，多个参数之间使用空格分隔。
 *              例如：java -jar Main.jar 1 2 3 4
 *              命令后使用-Dname=value方式可以设置jar包运行时的VM options参数，在程序中可使用System.getProperty(name)方式获取值
 *              System.getenv(name)可获取系统环境变量的值
 *              --server.port=8080 类似的参数是Springboot特别支持的启动参数类型，它会对这种类型的参数做相应的解析，java本身
 *              并不支持这种类型的参数
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
        System.out.println("dfz1: " + dfz1 + ";dfz2: " + dfz2 + ";dfz3: " + dfz3);
        ArrayList<Integer> integers = new ArrayList<>(0);
        integers.add(1);
        Properties properties = System.getProperties();
        System.out.println("-----------env-------------");
        properties.forEach((k,v)->System.out.println(k + ":" + v));
        Map<String, String> systemEnv = System.getenv();
        System.out.println("-----------systemEnv-------------");
        systemEnv.forEach((k, v) -> System.out.println(k + ":" + v));
    }

}