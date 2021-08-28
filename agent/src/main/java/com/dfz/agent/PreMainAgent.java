package com.dfz.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.stream.Stream;

/**
 * @Author DFZ
 * @Date 2021-08-13 17:49
 * @Description agent运行命令为 java -javaagent:agent-1.0-SNAPSHOT.jar=a=1,b=2,c=3 -jar annotation-1.0-SNAPSHOT.jar
 *                  注意： agent.jar 包中的 MANIFEST.MF 文件必须包含Premain-Class属性，且值为代理类的全路径名，如：Premain-Class: com.dfz.agent.PreMainAgent
 *
 *              agent premain类中的方法在 主运行jar的main方法之前执行完毕。因此在premain方法执行时， BootClassLoader 与 ExtClassLoader
 *              都已经将对应类加载完毕，此时在 Instrumentation 对象中已经可以拿到加载完的class，但此时，主运行jar中的类还未被加载。
 *
 *              我们可以在 Instrumentation 中注册钩子 {@link ClassFileTransformer}，此时当主运行jar中的类被加载时，会执行该钩子方法。
 */
public class PreMainAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        // 默认agent jar的类加载器是 AppClassLoader ，且与主运行jar是同一个类加载器
        // 如若在MANIFEST.MF中配置了Boot-Class-Path且agent jar在该path下，则agent jar由BootClassLoader完成加载
        System.out.println(PreMainAgent.class.getClassLoader());
        System.out.println("premain start");
        System.out.println(args);
        System.out.println("-----------------");
        System.out.println("加载的类数量：" + instrumentation.getAllLoadedClasses().length);
        System.out.println(Stream.of(instrumentation.getAllLoadedClasses()).filter(item -> item.getName()
                .startsWith("com.dfz.annotation")).count());
        Stream.of(instrumentation.getAllLoadedClasses()).filter(item -> item.getName()
                .startsWith("com.dfz.annotation")).forEach(System.out::println);
        System.out.println("1111111111");

        // instrumentation
        // 默认 isRetransformClassesSupported 与 isRedefineClassesSupported 都是false，必须在MANIFEST.MF文件中指定
        // Can-Redefine-Classes 和 Can-Retransform-Classes 为true，具体可参考pom文件中的定义
        System.out.println(instrumentation.isRetransformClassesSupported() + " " + instrumentation.isRedefineClassesSupported());
        instrumentation.addTransformer(new Transformer(), true);
    }

    public static class Transformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

            // 如何修改classfileBuffer Java字节码？
            // 以下都是猜测，需要能修改字节码之后，运行代码严重
            // 1. 在该agent加载之后，被加载进来的类，会被执行该 Transformer，生成新的字节码，然后加载到jvm内存中，实例化为Class对象
            //    那原始的字节码是如何被存储的？
            // 2. 当调用 instrumentation#removeTransformer 方法时，会使用原始的字节码，依次执行所有 canRetransform 为true的Transformer
            //    对象，那么调用两次removeTransformer方法是幂等的？假如在removeTransformer之前，实例化了一个对象，然后removeTransformer
            //    之后，新建的对象，与原来之前实例化的对象行为上有区别嘛？
            return classfileBuffer;
        }

    }

}
