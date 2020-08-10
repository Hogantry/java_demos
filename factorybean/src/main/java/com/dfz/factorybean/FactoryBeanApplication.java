package com.dfz.factorybean;

import com.dfz.factorybean.component.MyDemoFactoryBean;
import com.dfz.factorybean.component.MyDemoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @version V1.0
 * @author: DFZ
 * @description: FactoryBean 本身也是一个Bean，Bean的name为默认的name前加上&（&[name]），默认的name为FactoryBean生成的Bean的name
 *               如下列所示，MyDemoFactoryBean默认的Bean name为myDemoFactoryBean，则MyDemoFactoryBean在IOC容器中的那么为&myDemoFactoryBean，
 *               getObject()方法返回的Bean的那么为myDemoFactoryBean。
 *               如果自定义MyDemoFactoryBean的name为myService，则名字分别为 &myService 和 myService
 * @date: 2020/8/4 09:40
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class FactoryBeanApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyDemoFactoryBean.class);
//        String[] myDemoFactoryBeanNames = applicationContext.getBeanNamesForType(MyDemoFactoryBean.class);
//        String[] myDemoServiceBeanNames = applicationContext.getBeanNamesForType(MyDemoService.class);
//        System.out.println(Arrays.toString(myDemoFactoryBeanNames));
//        System.out.println(Arrays.toString(myDemoServiceBeanNames));

        // 跟踪源码
        MyDemoFactoryBean myDemoFactoryBean = applicationContext.getBean(MyDemoFactoryBean.class);
    }

}
