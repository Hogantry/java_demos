package com.dfz.agent.agentmain;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;

/**
 * @Author DFZ
 * @Date 2021-08-19 20:10
 * @Description
 */
public class Interceptor {

    @Advice.OnMethodEnter
    public static long enter(@Advice.This Thread thread, @Advice.Origin Method origin) {
        long start = System.currentTimeMillis();
        System.out.println("thread:" + thread.getName() + " enter thread timeMills:" + start);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        if (classLoader == null) {
            System.out.println("agent classLoader is null");
            try {
                origin.invoke(null, thread);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return start;
        }

        try {
//            Class reflectClass = Class.forName("com.dfz.agent.agentmain.CustomThread", true, classLoader);
//            Method startMethod = reflectClass.getMethod("start", Thread.class);

            Method startMethod = CustomThread.class.getDeclaredMethod("start", Thread.class);
            startMethod.invoke(null, thread);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return start;
    }

    @Advice.OnMethodExit
    public static void exit(@Advice.This Thread thread, @Advice.Origin Method origin) {
        long end = System.currentTimeMillis();
        System.out.println("thread:" + thread.getName() + " exit thread timeMills:" + end + ";");
    }

}
