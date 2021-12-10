package com.dfz.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @Author DFZ
 * @Date 2021-08-17 18:20
 * @Description
 */
public class HelloWorld {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Class<?> dynmicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World"))
                .make()
                .load(HelloWorld.class.getClassLoader())
                .getLoaded();
        Object instance = dynmicType.newInstance();
        String string = instance.toString();
        System.out.println(string);
        System.out.println(instance.getClass().getCanonicalName());
    }

}
