package com.dfz.resolvable.type;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ParameterizedTypeTest
 * @Description ParameterizedType 参数化类型，即泛型；例如：List<T>、Map<K,V>等带有参数化的对象;
 *              ParameterizedType 接口有三个方法：
 *                  1.getActualTypeArguments，返回参数化类型的数组，如上，则返回[T],[K,V]
 *                  2.getRawType，返回实际类型，如上，则返回List，Map
 *                  3.getOwnerType，如果getRawType返回的类是某个类的内部类，那么该方法可以获取外部类，否则返回null
 * @Author dfz
 * @Date 2019-06-26 09:30
 * @Version 1.0
 **/
public class ParameterizedTypeTest {

    /**
     * map: 获取ParameterizedType:class java.lang.String
     * map: 获取ParameterizedType:class com.dfz.resolvable.type.ParameterizedTypeTest
     * map: getOwnerType is null
     * map: getRawType:interface java.util.Map
     */
    private Map<String, ParameterizedTypeTest> map;

    /**
     * set1: 获取ParameterizedType:class java.lang.String
     * set1: getOwnerType is null
     * set1: getRawType:interface java.util.Set
     */
    private Set<String> set1;

    /**
     * clz: 获取ParameterizedType:?
     * clz: getOwnerType is null
     * clz: getRawType:class java.lang.Class
     */
    private Class<?> clz;

    /**
     * holder: 获取ParameterizedType:class java.lang.String
     * holder: getOwnerType:class com.dfz.resolvable.type.ParameterizedTypeTest
     * holder: getRawType:class com.dfz.resolvable.type.ParameterizedTypeTest$Holder
     */
    private Holder<String> holder;

    /**
     * list: 获取ParameterizedType:class java.lang.String
     * list: getOwnerType is null
     * list: getRawType:interface java.util.List
     */
    private List<String> list;

    /**
     * entry: 获取ParameterizedType:class java.lang.String
     * entry: 获取ParameterizedType:class java.lang.String
     * entry: getOwnerType:interface java.util.Map
     * entry: getRawType:interface java.util.Map$Entry
     */
    private Map.Entry<String, String> entry;

    private String[] strings;
    /**
     * str: is not ParameterizedType
     */
    private String str;

    /**
     * i: is not ParameterizedType
     */
    private Integer i;

    /**
     * set: is not ParameterizedType
     */
    private Set set;

    /**
     * aList: is not ParameterizedType
     */
    private List aList;

    static class Holder<V> {

    }

    public static void testParameterizedType() {
        Field f;
        try {
            Field[] fields = ParameterizedTypeTest.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                f = fields[i];
                Type genericType = f.getGenericType();
                System.out.println("变量名：" + f.getName());
                System.out.println("变量声明的类型：" + genericType.getTypeName());
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    for (Type type : parameterizedType.getActualTypeArguments()) {
                        System.out.println(f.getName() + ": 获取ParameterizedType:" + type);
                    }
                    if (parameterizedType.getOwnerType() != null) {
                        System.out.println(f.getName() + ": getOwnerType:" + parameterizedType.getOwnerType());
                    } else {
                        System.out.println(f.getName() + ": getOwnerType is null");
                    }
                    if (parameterizedType.getRawType() != null) {
                        System.out.println(f.getName() + ": getRawType:" + parameterizedType.getRawType());
                    } else {
                        System.out.println(f.getName() + ": getRawType is null");
                    }
                    System.out.println(f.getName() + ": is ParameterizedType ");
                } else {
                    if (genericType instanceof GenericArrayType) {
                        System.out.println(f.getName() + ": is GenericArrayType");
                    } else if (genericType instanceof WildcardType) {
                        System.out.println(f.getName() + ": is WildcardType");
                    } else if (genericType instanceof TypeVariable) {
                        System.out.println(f.getName() + ": is TypeVariable");
                    } else if ((genericType instanceof Class)) {
                        System.out.println(f.getName() + ": is Class");
                    }
                }
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            System.err.println("error" + e);
        }
    }


    public static void main(String[] args) {
        testParameterizedType();
    }

}
