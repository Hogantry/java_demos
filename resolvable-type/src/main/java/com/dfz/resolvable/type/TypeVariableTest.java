package com.dfz.resolvable.type;

import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.List;

/**
 * @ClassName TypeVariableTest
 * @Description TypeVariable 泛型的类型变量；指的是List<T>、Map<K,V>中的T，K，V等值；此外，还可以对类型变量加上extend限定，
 *              这样会有类型变量对应的上限；值得注意的是，类型变量的上限可以为多个，必须使用&符号相连接，
 *              例如 List< T extends Number & Serializable & Runnable>；其中，& 后必须为接口；
 *
 *              TypeVariable 接口有三个方法：
 *                  1.getBounds，类型对应的上限数组，默认为[Object.class]
 *                  2.getGenericDeclaration，获取声明该类型变量实体，可以是类或者是方法，也就是TypeVariableTest<T>中的TypeVariableTest
 *                  3.getName，获取类型变量在源码中定义的名称
 *                  4.getAnnotatedBounds(since 1.8)
 *
 *              泛型的声明包括在类上、方法上、构造函数上，并不能再字段上声明泛型。可通过如下方法获取
 *              class.getTypeParameters()、constructor.getTypeParameters()、method.getTypeParameters()
 * @Author dfz
 * @Date 2019-06-26 18:44
 * @Version 1.0
 **/
public class TypeVariableTest<T extends Number & Runnable & Serializable, V> {

    /**
     * TypeVariable
     */
    private T key;

    /**
     * TypeVariable
     */
    private V value;

    /**
     * GenericArrayType V[]-> V TypeVariable 两种混合起来了
     */
    private V[] values;
    /**
     * 原始类型，不仅仅包含我们平常所指的类，还包括枚举、数组、注解等；
     * 基本类型，也就是我们所说的java的基本类型，即int,float,double等
     */
    private String str;

    /**
     * 获取ParameterizedType List<T> -> T TypeVariable 两种混合起来了
     */
    private List<T> tList;

    private <M> void test (M a) {
        return;
    }

    /**
     * 从这个例子中可以看出来各种类型之间是相互在使用的
     * TypeVariable<D extends GenericDeclaration>
     * GenericDeclaration  All Known Implementing Classes: Class, Constructor, Method
     */
    public static void testTypeVariableTest() {
//        Field f;
//        try {
//            Field[] fields = TypeVariableTest.class.getDeclaredFields();
//            for (int i = 0; i < fields.length; i++) {
//                f = fields[i];
//                System.out.println("begin ******当前field:" + f.getName() + " *************************");
//                Type typeToHandle = f.getGenericType();
//                handleType(f.getName(), typeToHandle);
//                System.out.println("end ******当前field:" + f.getName() + " *************************");
//            }
//        } catch (Exception e) {
//            System.err.println("error: " + e);
//        }

        Method method = ReflectionUtils.findMethod(TypeVariableTest.class, "test", new Class[]{Object.class});
        Type[] parameterTypes = method.getGenericParameterTypes();
        for (Type parameterType : parameterTypes) {
            handleType(method.getName(), parameterType);
        }

    }

    public static void handleType(String fieldName, Type typeToHandle) {
        if (typeToHandle instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) typeToHandle;
            for (Type type : parameterizedType.getActualTypeArguments()) {
                System.out.println(fieldName + ": 获取ParameterizedType:" + type);
                if (type instanceof TypeVariable) {
                    printTypeVariable(fieldName, (TypeVariable) type);
                }
            }
            if (parameterizedType.getOwnerType() != null) {
                System.out.println(fieldName + ":getOwnerType:" + parameterizedType.getOwnerType());
            } else {
                System.out.println(fieldName + ":getOwnerType is null");
            }
            if (parameterizedType.getRawType() != null) {
                System.out.println(fieldName + ":getRawType:" + parameterizedType.getRawType());
            }
        } else if (typeToHandle instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) typeToHandle;
            System.out.println("GenericArrayType type :" + genericArrayType);
            Type genericComponentType = genericArrayType.getGenericComponentType();
            if (genericComponentType instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable) genericComponentType;
                printTypeVariable(fieldName, typeVariable);
            }
        } else if (typeToHandle instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) typeToHandle;
            printTypeVariable(fieldName, typeVariable);
        } else {
            System.out.println("type :" + typeToHandle);
        }
    }

    /**
     * 1、Type[] getBounds() 类型对应的上限，默认为Object
     * 2、D getGenericDeclaration()  获取声明该类型变量实体，也就是TypeVariableTest<T>中的TypeVariableTest
     * 3、String getName() 获取类型变量在源码中定义的名称；
     *
     * @param fieldName
     * @param typeVariable
     */
    private static void printTypeVariable(String fieldName, TypeVariable typeVariable) {
        for (Type type : typeVariable.getBounds()) {
            System.out.println(fieldName + ": TypeVariable getBounds " + type);
        }
        System.out.println("定义Class getGenericDeclaration: " + typeVariable.getGenericDeclaration());
        System.out.println("getName: " + typeVariable.getName());
    }

    public static void main(String[] args) {
        testTypeVariableTest();
    }

}
