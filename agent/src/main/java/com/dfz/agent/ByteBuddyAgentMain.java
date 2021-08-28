package com.dfz.agent;

import com.dfz.agent.agentmain.Interceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.concurrent.TimeUnit;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * @Author DFZ
 * @Date 2021-08-18 20:44
 * @Description
 */
public class ByteBuddyAgentMain {

    public static void premain(String args, Instrumentation instrumentation) throws UnmodifiableClassException {

        premain0(args, instrumentation);
        System.out.println("ByteBuddyAgentMain classLoad: " + ByteBuddyAgentMain.class.getClassLoader());
//        premain1(args, instrumentation);
    }

    /***
     * 在用于修改还未加载进jvm中的类，相当于redefine
     * 猜测大概逻辑是，加载字节码的时候，进行了字节码的修改，然后再继续执行链接、初始化等操作。
     * @param args
     * @param instrumentation
     */
    public static void premain0(String args, Instrumentation instrumentation) {
        new AgentBuilder.Default()
                .type(named("com.dfz.annotation.AnnotationApplication"))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {

//                        DynamicType.Builder<?> builder1 = builder.method(named("toBeModified")).intercept(FixedValue.value("modified"));
                        DynamicType.Builder<?> builder1 = builder.visit(Advice.to(LoggerAdvisor.class).on(named("toBeModified")));

                        DynamicType.Unloaded<?> dynamicType = builder1.make();
                        String fileName = dynamicType.getTypeDescription().getSimpleName() + ".class";
                        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                            outputStream.write(dynamicType.getBytes());
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return builder1;
                    }
                }).installOn(instrumentation);
    }

    /***
     * 用于修改已经加载进jvm中的类
     * 必须配置如下 ignore、with、type三个参数，具体原因不明
     *
     * 猜测实现思路，将已经加载的类重命名，然后生成新的类使用原类名，并将新类加载进jvm
     * 猜测如下：
     * 应该会禁止修改和删除原类的字段与方法签名，只可以添加字段或方法，以及修改原有方法中的逻辑
     *
     * @param args
     * @param instrumentation
     */
    public static void premain1(String args, Instrumentation instrumentation) {
        Thread thread = new Thread(() -> {
            System.out.println("hello bytebuddy agent by DFZ3");
            for (int i = 0; i < 10000L; i++) {

            }
        }, "DFZ3");

        new Thread(() -> {
            System.out.println("hello bytebuddy agent by LHR");
            for (int i=0; i < 10000L; i++) {

            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(() -> {
                System.out.println("hello bytebuddy agent by DFZ2");
                for (int i=0; i < 10000L; i++) {

                }
            }, "DFZ2").start();
            thread.start();

        }, "LHR").start();

        new AgentBuilder.Default()
//                .disableClassFormatChanges()
//                .ignore(new AgentBuilder.RawMatcher.ForElementMatchers(nameStartsWith("net.bytebuddy")))
                .ignore(ElementMatchers.noneOf(Thread.class))
//                .with(AgentBuilder.InitializationStrategy.NoOp.INSTANCE)
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
//                .with(AgentBuilder.TypeStrategy.Default.REDEFINE)
                .type(ElementMatchers.is(Thread.class))
                .transform(new AgentBuilder.Transformer() {
                    @Override
                    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {

                        System.out.println("transform method params classLoader: " + classLoader);

                        DynamicType.Builder<?> builder1 = builder.visit(Advice.to(Interceptor.class).on(named("start")));

                        DynamicType.Unloaded<?> dynamicType = builder1.make();
                        String fileName = dynamicType.getTypeDescription().getSimpleName() + ".class";
                        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                            outputStream.write(dynamicType.getBytes());
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return builder1;
                    }
                })
                .installOn(instrumentation);
    }

}
