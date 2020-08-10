package com.dfz.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * @ClassName AdaptiveTest
 * @Description 测试Adaptive /META-INF/dubbo/com.dfz.dubbo.adaptive.AdaptiveExt
 *              使用方式：1. 在类上加上@Adaptive注解的类，是最为明确的创建对应类型Adaptive类。所以他优先级最高。
 *                      2. 可以在方法上增加@Adaptive注解，注解中的value与链接中的参数的key一致，链接中的key对应的value就是
 *                         spi中的name，获取相应的实现类。如果没有配置key，则使用默认的key值，即接口的类名转换字符串:adaptive.ext
 *                         如果配置了key，则默认的类名不再转换
 *                      3. @SPI注解中的value是默认值，如果通过URL获取不到关于取哪个类作为Adaptive类的话，就使用这个默认值，
 *                         当然如果URL中可以获取到，就用URL中的。
 *
 * @Author dfz
 * @Date 2019-06-28 11:13
 * @Version 1.0
 **/
public class AdaptiveTest {

    public static void main(String[] args) {
        ExtensionLoader<AdaptiveExt> extensionLoader = ExtensionLoader.getExtensionLoader(AdaptiveExt.class);
        AdaptiveExt adaptiveExt = extensionLoader.getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test?adaptive.ext=cloud&t=cloud");
        System.out.println(adaptiveExt.echo("d", url));
    }

}
