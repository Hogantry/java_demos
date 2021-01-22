package com.dfz.dubbo.activate;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.List;

/**
 * @ClassName ActivateTest
 * @Description 1.根据ExtensionLoader.getActivateExtension中的group和搜索到的此类型的所有实例进行比较，
 *                如果group能匹配到，就是我们选择的，也就是在此条件下需要激活的，如果传入的group为null，则获取所有实例。
 *                group的判断是与url无关的，在SPI实现类中指定group，在获取时亦需手动执行group，（亦可都不指定），
 *                group的值一般为{@link Constants#PROVIDER}或者{@link Constants#CONSUMER}
 *              2.@Activate中的value参数是第二层过滤参数（第一层是通过group），在group校验通过的前提下，
 *                如果URL中的参数（k）与值（v）中的参数名同@Activate中的value值一致或者包含，那么才会被选中。
 *                相当于加入了value后，条件更为苛刻点，需要URL中有此参数并且，参数必须有值。value的具体判断如下：
 *                获取@{@link Activate}注解的value值，如果value为空，则判断匹配，否则，遍历url的params值，判断value中的
 *                值（或.value[i]的值）是否在params中，且有值，则匹配。getActivateExtension方法的第二参数，是用作额外添加
 *                可过滤的实例。
 *              3.@Activate的order参数对于同一个类型的多个扩展来说，order值越小，优先级越高。
 * @Author dfz
 * @Date 2019-06-28 14:48
 * @Version 1.0
 **/
public class ActivateTest {

    public static void main(String[] args) {
        ExtensionLoader<ActivateExt> extensionLoader = ExtensionLoader.getExtensionLoader(ActivateExt.class);
        // 因为WithValueActivateExt类的注解中包含value值，且值为value，则url中如果params中没有value，则WithValueActivateExt类会被过滤掉
        URL url = URL.valueOf("test://localhost/test?value2=b");
//        List<ActivateExt> activateExts = extensionLoader.getActivateExtension(url, new String[]{}, "default_group");
//        List<ActivateExt> activateExts = extensionLoader.getActivateExtension(url, new String[]{}, "group2");
//        List<ActivateExt> activateExts = extensionLoader.getActivateExtension(url, new String[]{}, "value");
//        List<ActivateExt> activateExts = extensionLoader.getActivateExtension(url, new String[]{}, "order");
        List<ActivateExt> activateExts = extensionLoader.getActivateExtension(url, new String[]{}, null);
        System.out.println(activateExts.size());
        System.out.println(activateExts.get(0).getClass());
    }

}
