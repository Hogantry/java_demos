package com.dfz.resolvable.type;

import java.lang.reflect.*;
import java.util.List;

/**
 * 参考：https://blog.csdn.net/u012881904/article/details/80813294
 *
 * @ClassName GenericArrayTypeTest
 * @Description GenericArrayType 泛型数组，描述的是形如：A<T>[]、T[]、List<String>[][]类型变量和原始类型
 *              GenericArrayType 接口有一个方法：
 *                  1.getGenericComponentType，返回泛型数组的原始类型，如上，则返回A<T>、T、List<String>[]，
 *                    他们又分别是ParameterizedType、TypeVariable、GenericArrayType类型
 *
 *              Field：获取Filed的type类型，使用Field的getGenericType()方法
 *              Method: 获取Method的所有参数的type类型，使用Method的getGenericParameterTypes()方法
 * @Author dfz
 * @Date 2019-06-26 18:21
 * @Version 1.0
 **/
public class GenericArrayTypeTest<T> {

    /**
     * GenericArrayType type: java.util.List<java.lang.String>[]
     * genericComponentType: java.util.List<java.lang.String>
     * GenericArrayType type: T[]
     * genericComponentType: T
     * ParameterizedType type: java.util.List<java.lang.String>
     * type: class [Ljava.lang.String;
     * type: class [Lcom.dfz.resolvable.type.GenericArrayTypeTest;
     */
    public void genericArrayType(List<String>[] pTypeArray, T[] vTypeArray
            , List<String> list, String[] strings, GenericArrayTypeTest[] test) {
    }

    public static void testGenericArrayType() {
        Method[] declaredMethods = GenericArrayTypeTest.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (!method.getName().equalsIgnoreCase("genericArrayType")) {
                continue;
            }
            System.out.println("declare Method:" + method);
            /**
             * 获取当前参数所有的类型信息
             */
            Type[] types = method.getGenericParameterTypes();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    System.out.println("ParameterizedType type: " + type);
                    System.out.println("----------");
                } else if (type instanceof GenericArrayType) {
                    System.out.println("GenericArrayType type: " + type);
                    GenericArrayType genericArrayType = (GenericArrayType) type;
                    Type genericComponentType = genericArrayType.getGenericComponentType();
                    /**
                     * 获取泛型数组中元素的类型，要注意的是：无论从左向右有几个[]并列，
                     * 这个方法仅仅脱去最右边的[]之后剩下的内容就作为这个方法的返回值。
                     */
                    if (genericComponentType instanceof ParameterizedType) {
                        System.out.println("ParameterizedType type: " + genericComponentType);
                    }
                    System.out.println("genericComponentType: " + genericComponentType);
                    System.out.println("----------");
                } else if (type instanceof WildcardType) {
                    System.out.println("WildcardType type: " + type);
                    System.out.println("----------");
                } else if (type instanceof TypeVariable) {
                    System.out.println("TypeVariable type: " + type);
                    System.out.println("----------");
                } else {
                    System.out.println("type: " + type);
                    System.out.println("----------");
                }
            }
        }
    }

    public static void main(String[] args) {
        testGenericArrayType();
    }

}
