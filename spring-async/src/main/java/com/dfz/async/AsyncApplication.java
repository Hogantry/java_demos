package com.dfz.async;

import com.dfz.async.component.NormalBean;
import com.dfz.async.config.AsyncConfig;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.AsyncAnnotationAdvisor;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.AsyncConfigurationSelector;
import org.springframework.scheduling.annotation.ProxyAsyncConfiguration;

import java.io.IOException;

/**
 * @version V1.0
 * @author: DFZ
 * @description: @EnableAsync 底层是通过AOP来实现的，所有要想看懂他的实现原理，前提是要了解AOP的原理。
 *               @EnableAsync 通过@Import导入{@link AsyncConfigurationSelector}， 再引入{@link ProxyAsyncConfiguration}，
 *               再引入{@link AsyncAnnotationBeanPostProcessor}，他实现了{@link BeanFactoryAware}和{@link BeanPostProcessor}，
 *               生命周期函数，前者先执行，完成了{@link AsyncAnnotationAdvisor}的初始化，切面可以理解为切点和增强的合体。而后，后者在实例化bean
 *               时，完成动态代理的创建，实现AOP的注入过程。
 * @date: 2020/8/11 13:23
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class AsyncApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AsyncConfig.class);
        NormalBean normalBean = context.getBean(NormalBean.class);
        normalBean.call();
        System.in.read();
    }

}
