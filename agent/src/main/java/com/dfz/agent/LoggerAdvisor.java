package com.dfz.agent;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author DFZ
 * @Date 2021-08-18 18:18
 * @Description
 */
public class LoggerAdvisor {

    @Advice.OnMethodEnter
    public static long onMethodEnter(@Advice.Origin Method method, @Advice.AllArguments Object[] args) {
        System.out.println("Enter " + method.getName() + " with arguments: " + Arrays.toString(args));
        return System.currentTimeMillis();
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method, @Advice.AllArguments Object[] args, @Advice.Return Object ret, @Advice.Enter long start) {
        long end = System.currentTimeMillis();
        System.out.println("Enter " + method.getName() + " with arguments: " + Arrays.toString(args) + "; return: " + ret + "; spend time: " + (end - start) / 1000);
    }


}
