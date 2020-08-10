package com.dfz.resources;

import java.net.URL;

/**
 * @ClassName Main
 * @Description Main
 *              class.getResource(A) 对A的路径做了解析，然后调用classLoader.getResource()方法
 *                  A 不以 / 开头： 获取当前类所在路径下的A资源
 *                  A 以 / 开头： 获取classpath路径下的A资源
 *
 *              classLoader.getResource()与ClassLoader.getSystemResource()不支持以/开头的路径，从classpath路径下获取资源
 *
 *              三个方法，在资源不存在时，均会返回null。
 * @Author dfz
 * @Date 2019-09-06 15:32
 * @Version 1.0
 **/
public class JavaResource {

    public static void main(String[] args) {
        new JavaResource().printResourcesPath1();
        new JavaResource().printResourcesPath2();
        new JavaResource().printResourcesPath3();
    }

    private void printResourcesPath1() {
        URL resourceA = this.getClass().getResource("a.txt");
        URL resourceA1 = this.getClass().getResource("b.txt");
        URL resourceB = this.getClass().getResource("/a.txt");

        URL resourceC = this.getClass().getResource("com/dfz/resources/a.txt");
        URL resourceD = this.getClass().getResource("/com/dfz/resources/a.txt");

        println("resourceA", resourceA);
        println("resourceA1", resourceA1);
        println("resourceB", resourceB);
        println("resourceC", resourceC);
        println("resourceD", resourceD);
        System.out.println("------------------------------------------");
    }

    private void printResourcesPath2() {
        URL resourceA = this.getClass().getClassLoader().getResource("a.txt");
        URL resourceB = this.getClass().getClassLoader().getResource("/a.txt");

        URL resourceC = this.getClass().getClassLoader().getResource("com/dfz/resources/a.txt");
        URL resourceD = this.getClass().getClassLoader().getResource("/com/dfz/resources/a.txt");

        println("resourceA", resourceA);
        println("resourceB", resourceB);
        println("resourceC", resourceC);
        println("resourceD", resourceD);
        System.out.println("------------------------------------------");
    }

    private void printResourcesPath3() {
        URL resourceA = ClassLoader.getSystemResource("a.txt");
        URL resourceB = ClassLoader.getSystemResource("/a.txt");

        URL resourceC = ClassLoader.getSystemResource("com/dfz/resources/a.txt");
        URL resourceD = ClassLoader.getSystemResource("/com/dfz/resources/a.txt");

        println("resourceA", resourceA);
        println("resourceB", resourceB);
        println("resourceC", resourceC);
        println("resourceD", resourceD);
    }

    private void println(String key, URL resource) {
        if (resource == null) {
            System.out.println(key + " is null");
            return;
        }
        System.out.println(key + ": " + resource.getPath());
    }

}
