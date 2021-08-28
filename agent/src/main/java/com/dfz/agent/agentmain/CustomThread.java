package com.dfz.agent.agentmain;

import java.lang.reflect.Field;

/**
 * @Author DFZ
 * @Date 2021-08-19 20:14
 * @Description
 */
public class CustomThread {

    public static void start(Thread thread) {
        try {
            System.out.println("welcome to my custom thread");
            Field field = Thread.class.getDeclaredField("target");
            field.setAccessible(true);
            Object targetValue = field.get(thread);
            if (targetValue instanceof Runnable) {
                Runnable runnable = (Runnable) targetValue;
                field.set(thread, new CustomRunnable(runnable));
            }
        } catch (Throwable e) {
            System.out.println(e);;
        }
    }

}
