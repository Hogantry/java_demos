package com.dfz.factorybean.component;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @author: DFZ
 * @description: my factorybean
 * @date: 2020/8/4 09:40
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Component("myService")
public class MyDemoFactoryBean implements FactoryBean<MyDemoService> {
    @Override
    public MyDemoService getObject() throws Exception {
        return new MyDemoService();
    }

    @Override
    public Class<?> getObjectType() {
        return MyDemoService.class;
    }
}
