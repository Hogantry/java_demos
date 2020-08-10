package com.dfz.spring.autowired;

import com.dfz.spring.autowired.config.HelloConfig;
import com.dfz.spring.autowired.controller.HelloController;
import com.dfz.spring.autowired.dao.HelloDao;
import com.dfz.spring.autowired.service.HelloService;
import com.dfz.spring.autowired.service.impl.HelloService2Impl;
import com.dfz.spring.autowired.service.impl.HelloServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Arrays;

/**
 * @version V1.0
 * @author: DFZ
 * @description: autowired
 * @date: 2020/4/27 09:19
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class AutowiredApplication {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HelloConfig.class);
        HelloService bean = context.getBean(HelloService2Impl.class);
        System.out.println(bean);
        String[] names = context.getBeanNamesForType(HelloDao.class);
        System.out.println(names.length);
        Arrays.stream(names).forEach(System.out::println);

        System.out.println("------------");

        HelloController controller = context.getBean(HelloController.class);
        System.out.println(controller);
        String[] names2 = context.getBeanNamesForType(HelloService2Impl.class);
        Arrays.stream(names2).forEach(System.out::print);
//        System.in.read();
    }

}
