package com.dfz.bytebuddy;

import com.dfz.bytebuddy.aop.LoggerAdvisor;
import com.dfz.bytebuddy.aop.Service;
import com.dfz.bytebuddy.aop.Service2;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * @Author DFZ
 * @Date 2021-08-17 18:15
 * @Description
 */
public class Main {

    /***
     * ByteBuddy 如果使用 WRAPPER 或者 CHILD_FIRST 则每次生成新类时都使用新的类加载器去加载。
     * @param args
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
//        subClass();
//        aopDemo();
        reDefineClass();
//        reDefineClassFromOutSide();
    }

    /**
     * 模拟实现AOP
     */
    public static void aopDemo() throws InstantiationException, IllegalAccessException {
        ByteBuddy byteBuddy = new ByteBuddy();

        DynamicType.Unloaded<Service> dynamicService = byteBuddy
                .subclass(Service.class)
                .method(ElementMatchers.any())
                // Advice是在原方法前后执行增强，MethodDelegation则是完全委托，后者可通过注解传参的方式实现Advice的效果
                .intercept(Advice.to(LoggerAdvisor.class))
                .make();

        String fileName = dynamicService.getTypeDescription().getSimpleName() + ".class";
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            outputStream.write(dynamicService.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class<? extends Service> serviceAop = dynamicService
                .load(Main.class.getClassLoader())
                .getLoaded();

        Service service = serviceAop.newInstance();
        service.bar(123);
        service.foo(456);
    }

    /***
     * 重定义已存在的class，之后用该class实例化对象
     *
     * bytebuddy的 redefine 和 rebase ：不能直接替换吗？那有什么用？不太清楚，待深入了解
     * 1. 如果重定义已被jvm加载的类，则必须指定新生成类的名字，且不与已
     * 加载的任何类重名，否则新类也无法加载进jvm使用。此时 redefine 与 rebase 的作用就是生成新类，与原类没有关系。
     * 2. 如果重定义没有被加载的类，则最好不要重命名类名，这样，在 redefine 或 rebase 之后，jvm去加载类时，就是使用的被重写后的类
     *
     * 这里注意区分 java agent 中的 Instrumentation 的 Transformer功能。
     */
    public static void reDefineClass() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        ByteBuddyAgent.install();
//        Service service = new Service();
//        service.bar(1);

        TypePool typePool = TypePool.Default.of(Main.class.getClassLoader());
        TypeDescription description = typePool.describe("com.dfz.bytebuddy.aop.Service").resolve();

        DynamicType.Unloaded<Object> dynamicType = new ByteBuddy().rebase(description, ClassFileLocator.ForClassLoader.of(Main.class.getClassLoader()))
                .method(named("bar"))
                .intercept(MethodDelegation.to(Service2.class))
                .make();

        System.out.println(dynamicType.getTypeDescription().getName());
        String fileName = dynamicType.getTypeDescription().getSimpleName() + ".class";
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            outputStream.write(dynamicType.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClassReloadingStrategy classReloadingStrategy = ClassReloadingStrategy.fromInstalledAgent();

        Object o = dynamicType.load(Service.class.getClassLoader(), classReloadingStrategy)
                .getLoaded().newInstance();
        Method bar = o.getClass().getDeclaredMethod("bar", int.class);
        bar.invoke(o, 1);
//        service.bar(2);

    }

    // 暂时运行不起来
    public static void reDefineClassFromOutSide() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        ByteBuddyAgent.install();
        ClassLoader classLoader = Main.class.getClassLoader();
        TypePool typePool = TypePool.Default.of(classLoader);
        TypeDescription typeDefinitions = typePool.describe("com.dfz.agent.Service3").resolve();
        File file = new File("D:\\WorkSpace\\repos\\demos\\agent\\target\\classes\\");
        DynamicType.Unloaded<Object> dynamicType = new ByteBuddy().redefine(typeDefinitions, new ClassFileLocator.ForFolder(file))
                .method(named("bar"))
                .intercept(MethodDelegation.to(Service2.class))
//                .name("Service4")
                .make();

        System.out.println(dynamicType.getTypeDescription().getName());
        String fileName = dynamicType.getTypeDescription().getSimpleName() + ".class";
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            outputStream.write(dynamicType.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        dynamicType.load(Thread.currentThread().getContextClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

        Object o = dynamicType.load(classLoader)
                .getLoaded().newInstance();
        Method bar = o.getClass().getDeclaredMethod("bar", int.class);
        bar.invoke(o, 1);
    }


    /***
     * 在已有类的基础上生成新的类，基本都是通过继承的方式实现
     */
    private static void subClass() throws FileNotFoundException, InstantiationException, IllegalAccessException {
        ByteBuddy byteBuddy = new ByteBuddy();
//                .with(new NamingStrategy.AbstractBase() {
//                    @Override
//                    protected String name(TypeDescription superClass) {
//                        return null;
//                    }
//                });

        DynamicType.Unloaded<UserType> dynamicType = byteBuddy
                // 创建新类，继承自UserType
                .subclass(UserType.class)
                // 指定新类的名称，不指定，Bytebuddy会使用默认的NamingStrategy去生成
//                .name("UserType$D")
                // 筛选方法，所有不在Object类中定义的方法（UserType直接继承自Object，则这里默认是获取所有定义在UserType类中的方法）
                .method(not(isDeclaredBy(Object.class)))
                // 方法实现，筛选的方法的具体实现，委托给了interceptor字段，会在该字段的所属类中，找最匹配的方法来执行
                .intercept(MethodDelegation.toField("interceptor"))
                // 添加字段，在新类中定义新的字段
                .defineField("interceptor", Interceptor.class, Visibility.PRIVATE)
                // 实现接口，新类实现接口，可指定多个
                .implement(InterceptorAccessor.class)
                // 接口实现，新类实现的新接口的方法实现
                .intercept(FieldAccessor.ofBeanProperty())
                // 编译，生成新类的字节码
                .make();

        String fileName = dynamicType.getTypeDescription().getSimpleName() + ".class";
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            outputStream.write(dynamicType.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Class<? extends UserType> dynamicUserType = dynamicType
                // 这里默认使用 ClassLoadingStrategy.Default.WRAPPER 类加载策略
//                WRAPPER：创建一个新的Wrapping类加载器，父类加载器是传入类加载器
//                CHILD_FIRST：类似上面，但是子加载器优先负责加载目标类，相当于破坏了双亲委派模式
//                INJECTION：利用反射机制注入动态类型，即使用用户传入的类加载器加载
                .load(Main.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        System.out.println(dynamicUserType.getClassLoader());
        System.out.println(dynamicUserType.getClassLoader().getParent());
        System.out.println(Main.class.getClassLoader());

        System.out.println("-----------------");

        InstanceCreator factory = new ByteBuddy()
                .subclass(InstanceCreator.class)
                .method(not(isDeclaredBy(Object.class)))
                .intercept(MethodDelegation.toConstructor(dynamicUserType))
                .make()
                .load(dynamicUserType.getClassLoader())
                .getLoaded().newInstance();

        UserType userType = (UserType) factory.makeInstance();
        ((InterceptorAccessor) userType).setInterceptor(new HelloWorldInterceptor());
        String s = userType.doSomething();
        System.out.println(s);


        DynamicType.Unloaded<Function> unloaded = byteBuddy
                .subclass(Function.class)
                .method(ElementMatchers.named("apply"))
                // 拦截Function.apply调用，委托给GreetingInterceptor处理
                // 如果直接写类，则代理给类的静态方法，如果是代理给类的实例方法，则下面必须new一个GreetingInterceptor对象
                .intercept(MethodDelegation.to(GreetingInterceptor.class))
                .make();
        String fileName2 = unloaded.getTypeDescription().getSimpleName() + ".class";
        try (FileOutputStream outputStream = new FileOutputStream(fileName2)) {
            outputStream.write(unloaded.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class<? extends java.util.function.Function> dynamicType2 = unloaded
                .load(Main.class.getClassLoader())
                .getLoaded();
        System.out.println(dynamicType2.getClassLoader());
        System.out.println(dynamicType2.getClassLoader().getParent());
        System.out.println(Main.class.getClassLoader());
        System.out.println(dynamicType2.newInstance().apply("abc"));
    }

    public static class GreetingInterceptor {
        // 方法签名随意
        // 如果该方法存在，优先使用该方法
//        public static Object greet(Object argument) {
//            return "Hello from " + argument;
//        }

        // 该注解可以用在返回值、参数上，提示ByteBuddy禁用严格的类型检查
        @RuntimeType
        public static Object greet(@AllArguments Object[] allArguments,
                                   @Origin Method method) {

            return "Hello from " + Arrays.toString(allArguments) + ", method: " + method.getName();
        }
    }

    public static class UserType {
        public String doSomething() {
            return "null";
        }
    }

    public interface Interceptor {
        String doSomethingElse();
    }

    public interface InterceptorAccessor {
        Interceptor getInterceptor();

        void setInterceptor(Interceptor interceptor);
    }

    public interface InstanceCreator {
        Object makeInstance();
    }

    public static class HelloWorldInterceptor implements Interceptor {

        @Override
        public String doSomethingElse() {
            return "Hello World";
        }
    }

}
