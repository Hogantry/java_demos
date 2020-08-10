package com.dfz.resolvable.type;

import java.lang.reflect.*;
import java.util.List;

/**
 * @ClassName WildcardTypeTest
 * @Description WildcardTypeTest, 泛型表达式 表示的仅仅是类似 ? extends T、? super K 这样的通配符表达式
 *              WildcardType 接口有两个方法：1.getUpperBounds，获得泛型表达式上界，默认是Object
 *                                        2.getLowerBounds，获得泛型表达式下界，默认是null
 * @Author dfz
 * @Date 2019-06-27 10:18
 * @Version 1.0
 **/
public class WildcardTypeTest {

    /**
     * 1、 a: 获取ParameterizedType:? extends java.lang.Number
     * 2、上界：class java.lang.Number
     */
    private List<? extends Number> a;

    /**
     * b: 获取ParameterizedType:? super java.lang.String
     * 上届：class java.lang.Object
     * 下届：class java.lang.String
     */
    private List<? super String> b;

    /**
     * c: 获取ParameterizedType:class java.lang.String
     */
    private List<String> c;

    /**
     * aClass: 获取ParameterizedType:?
     * 上届：class java.lang.Object
     */
    private Class<?> aClass;

    private String wangji;

    /**
     * 多种数据进行混合
     */
    public static void testWildcardType() {
        Field f;
        try {
            Field[] fields = WildcardTypeTest.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                f = fields[i];
                System.out.println("begin ******当前field:" + f.getName() + " *************************");
                if (f.getGenericType() instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) f.getGenericType();
                    for (Type type : parameterizedType.getActualTypeArguments()) {
                        System.out.println(f.getName() + ": 获取ParameterizedType:" + type);
                        if (type instanceof WildcardType) {
                            printWildcardType((WildcardType) type);
                        }
                    }
                } else if (f.getGenericType() instanceof GenericArrayType) {
                    GenericArrayType genericArrayType = (GenericArrayType) f.getGenericType();
                    System.out.println("GenericArrayType type :" + genericArrayType);
                    Type genericComponentType = genericArrayType.getGenericComponentType();
                    if (genericComponentType instanceof WildcardType) {
                        printWildcardType((WildcardType) genericComponentType);
                    }
                } else if (f.getGenericType() instanceof TypeVariable) {
                    TypeVariable typeVariable = (TypeVariable) f.getGenericType();
                    System.out.println("typeVariable:" + typeVariable);

                } else {
                    System.out.println("type :" + f.getGenericType());
                    if (f.getGenericType() instanceof WildcardType) {
                        printWildcardType((WildcardType) f.getGenericType());
                    }
                }
                System.out.println("end ******当前field:" + f.getName() + " *************************");
            }
        } catch (Exception e) {
            System.err.println("error " + e);
        }
    }

    private static void printWildcardType(WildcardType wildcardType) {
        for (Type type : wildcardType.getUpperBounds()) {
            System.out.println("上界：" + type);
        }
        for (Type type : wildcardType.getLowerBounds()) {
            System.out.println("下界：" + type);
        }
    }

    public static void main(String[] args) {
        testWildcardType();
    }
}
