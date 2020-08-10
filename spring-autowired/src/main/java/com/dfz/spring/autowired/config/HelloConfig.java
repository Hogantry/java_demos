package com.dfz.spring.autowired.config;

import com.dfz.spring.autowired.dao.HelloDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @version V1.0
 * @author: DFZ
 * @description: config
 * @date: 2020/4/27 09:23
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Configuration
@ComponentScan({"com.dfz.spring.autowired.controller", "com.dfz.spring.autowired.dao", "com.dfz.spring.autowired.service"})
public class HelloConfig {

    @Bean("helloDao2")
    public HelloDao helloDao() {
        return new HelloDao();
    }
}
