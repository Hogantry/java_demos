package com.dfz.resolvable.type.child;

import org.springframework.core.ResolvableType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @ClassName Child
 * @Description Child
 * @Author dfz
 * @Date 2019-07-08 11:09
 * @Version 1.0
 **/
public class Child extends Parent<String> implements IParent<Long> {

    public static void main(String[] args) {
        Type genericSuperclassType = Child.class.getGenericSuperclass();
        if (genericSuperclassType instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclassType)
                    .getActualTypeArguments();
            for (Type argumentType : actualTypeArguments) {
                System.out.println("父类ParameterizedType.getActualTypeArguments:" + argumentType);
            }
        }
        /**
         * 这里获取父接口中的泛型参数
         */
        Type[] genericInterfacesTypes = Child.class.getGenericInterfaces();
        for (Type interfaceType : genericInterfacesTypes) {
            if (interfaceType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) interfaceType)
                        .getActualTypeArguments();
                for (Type argumentType : actualTypeArguments) {
                    System.out.println("父接口ParameterizedType.getActualTypeArguments:" + argumentType);
                }
            }
        }


        /**
         言归正传，下面讲解ResolvableType。ResolvableType为所有的java类型提供了统一的数据结构以及API
         ，换句话说，一个ResolvableType对象就对应着一种java类型。
         我们可以通过ResolvableType对象获取类型携带的信息
         （举例如下）：
         1.getSuperType()：获取直接父类型
         2.getInterfaces()：获取接口类型
         3.getGeneric(int...)：获取类型携带的泛型类型
         4.resolve()：Type对象到Class对象的转换

         另外，ResolvableType的构造方法全部为私有的，我们不能直接new，只能使用其提供的静态方法进行类型获取：
         1.forField(Field)：获取指定字段的类型
         2.forMethodParameter(Method, int)：获取指定方法的指定形参的类型
         3.forMethodReturnType(Method)：获取指定方法的返回值的类型
         4.forClass(Class)：直接封装指定的类型
         */
        ResolvableType superResolvableType = ResolvableType.forClass(Child.class).getSuperType();
        System.out.println("supper:" + superResolvableType.resolveGenerics()[0]);

        ResolvableType superInterfaceResolvableType = ResolvableType.
                forClass(Child.class).getInterfaces()[0];
        System.out.println("supper:" + superInterfaceResolvableType.resolveGenerics()[0]);

    }
}
