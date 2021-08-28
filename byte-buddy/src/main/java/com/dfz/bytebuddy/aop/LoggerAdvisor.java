package com.dfz.bytebuddy.aop;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Author DFZ
 * @Date 2021-08-18 18:18
 * @Description
 */
public class LoggerAdvisor {

    @Advice.OnMethodEnter
    public static long onMethodEnter(@Advice.Origin Method method, @Advice.AllArguments Object[] args) {
        if (!Objects.isNull(method.getAnnotation(Log.class))) {
            System.out.println("Enter " + method.getName() + "with arguments: " + Arrays.toString(args));
        }
        return System.currentTimeMillis();
    }

    /***
     *
     * @param method @Advice.Origin 原方法
     * @param args @Advice.AllArguments 方法的所有参数
     * @param ret @Advice.Return 方法的返回值
     * @param enter @Advice.Enter 上面@Advice.OnMethodEnter 注解标注的方法的返回值，注意，这里的类型必须一直，防止报错
     */
    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method, @Advice.AllArguments Object[] args, @Advice.Return Object ret, @Advice.Enter long enter) {
        if (!Objects.isNull(method.getAnnotation(Log.class))) {
            System.out.println("Enter " + method.getName() + "with arguments: " + Arrays.toString(args) + " return: " + ret + " enter: " +enter);
        }
    }


}
