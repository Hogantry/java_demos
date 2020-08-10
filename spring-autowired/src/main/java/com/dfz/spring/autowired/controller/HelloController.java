package com.dfz.spring.autowired.controller;

import com.dfz.spring.autowired.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

/**
 * @version V1.0
 * @author: DFZ
 * @description: IOC容器中存在多个HelloService实例，可通过@Qualifier来指定使用具体某一个。
 *               当多个实例的类上都没有使用@Qualifier注解，则此处可使用@Qualifier(bean名称)来指定具体某一个
 *               当其中某个或某些类上使用了@Qualifier注解，则此处使用的@Qualifier注解的value值必须和需要指定的类上的注解的value值一致(两者可同时为空串)，才可完成注入
 *               当使用例如@Qualifier("abc")注解到类上时，并没有给该类生成的bean添加别名abc，如何实现的呢？
 *               具体实现原理可参考 {@link AutowiredAnnotationBeanPostProcessor } ，结合着将 @Primary注解一起看了。
 * @date: 2020/4/27 09:21
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Controller
public class HelloController {

    @Autowired
    @Qualifier("abcd")
    private HelloService helloService;

    @Override
    public String toString() {
        return "HelloController{" +
                "helloService=" + helloService +
                '}';
    }
}
