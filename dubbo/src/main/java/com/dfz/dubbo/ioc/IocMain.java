package com.dfz.dubbo.ioc;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;

/**
 * @ClassName IocMain
 * @Description Dubbo的IOC实现，主要是injectExtension()方法，实现IOC的思路如下：
 *              1、通过getMethods()方法获取类及其父类中所有的public方法，具体可参考{@link com.dfz.reflection.ReflectionMain}
 *              2、遍历第一步获取的所有方法，做三个判断：a.判断方法名是否是set开头；b.方法参数是否有且只有一个；c.方法是否是public的（该判断实属多余）
 *              3、如果符合第二步的条件，再判断该方法是否有{@link DisableInject}注解，有则直接过滤该方法，没有则继续第四步
 *              4、获取该方法对应的属性名，通过该属性名称，和方法参数类型，从objectFactory中查找对象，若有，则反射调用该方法，完成注入，没查到则跳过该方法，继续第二步的遍历操作
 *              注意：第一步中获取的方法，是包含static方法的，即，在dubbo中，static方法的属性值也能完成自动注入功能，这点与spring有点区别。
 * @Author dfz
 * @Date 2019-07-12 13:33
 * @Version 1.0
 **/
public class IocMain {

    public static void main(String[] args) {
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("dubbo");
    }
}
