package com.dfz.reflection;

import java.lang.reflect.Method;

/**
 * @ClassName ReflectionMain
 * @Description getMethods()：
 *                  1. 类调用 -- 获取类及其所有父类中所有的public方法(protected也不行)，不论是否是static，以及所有父接口中的所有default方法
 *                  2. 接口调用 -- 获取在本接口及其父接口中定义的所有方法(接口中定义的方法都是public的），包括JDK1.8新增的默认方法
 *              getDeclaredMethods():
 *                  1. 类调用 -- 获取仅在本类中定义的所有方法
 *                  2. 接口调用 -- 获取仅在本接口中定义的所有方法，包括JDK1.8新增的默认方法
 * @Author dfz
 * @Date 2019-07-12 11:25
 * @Version 1.0
 **/
public class ReflectionMain {

    public static void main(String[] args) {

//        Method[] methods = Reflection.class.getMethods();
//        for (Method method : methods) {
//            System.out.println(method.getName());
//        }

//        Method[] methods1 = Reflection.class.getDeclaredMethods();
//        for (Method method : methods1) {
//            System.out.println(method.getName());
//        }

//        Method[] methods2 = ParentInterface.class.getMethods();
//        for (Method method : methods2) {
//            System.out.println(method.getName());
//        }

        Method[] methods2 = ParentInterface.class.getDeclaredMethods();
        for (Method method : methods2) {
            System.out.println(method.getName());
        }

    }

}
