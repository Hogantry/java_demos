package com.dfz.async;

import com.dfz.async.component.NormalBean;
import com.dfz.async.config.AsyncConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @version V1.0
 * @author: DFZ
 * @description: async
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
