package com.dfz.resolvable.type;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

/**
 * @version V1.0
 * @author: DFZ
 * @description: Class类也是继承自Type接口，内部关于Type的相关方法如下：
 *               1.getTypeParameters，返回类上的泛型信息，例如ClassTest<T>，返回[T]，否则返回空数组
 *               2.getGenericSuperClass()，返回该类的父类的泛型类型，例如Chinese extend People<String,Integer,Double>{}，
 *                 返回的是People<String,Integer,Double>，如果没有父类，返回的是Object的Class实例，这里Chinese类的getTypeParameters
 *                 方法就会返回空数组
 *               3.getGenericInterfaces()，返回该类的实现的接口们的泛型类型，没有实现任何接口，返回空数组
 * @date: 2020-01-02 17:04
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ClassTest<T> {

    public <U> ClassTest(){
        printType(People.class.getGenericSuperclass().toString(), People.class.getGenericSuperclass());
        printType(Chinese.class.getGenericSuperclass().toString(), Chinese.class.getGenericSuperclass());
        Type[] types = Chinese.class.getGenericInterfaces();
        System.out.println( types.length );
        for(Type t: types){
            printType(t.toString(), t);
        }
        Type[] types1 = Mars.class.getGenericInterfaces();
        System.out.println( types1.length );
        for( Type t : types1 ){
            printType( t.toString(),t );
        }
    }

    public static void printType(String name, Type type){
        if( type instanceof Class ){
            System.out.println("the type of " + name + " is : Class");
        }else if( type instanceof ParameterizedType){
            System.out.println("the type of " + name + " is : ParameterizedType");
        }else if( type instanceof GenericArrayType){
            System.out.println("the type of " + name + " is : GenericArrayType");
        }else if( type instanceof TypeVariable){
            System.out.println("the type of " + name + " is : TypeVariable");
        }

    }

}

interface Walk<R>{}
interface SpeakChinese<H>{}
interface UseChopsticks<M>{}
class People<T,V,S> implements Walk<Short>{

}
class Chinese extends People<String,Integer,Double> implements SpeakChinese<String>,UseChopsticks<Double>{

}
class Mars extends People<String,Double, List<String>>{

}
