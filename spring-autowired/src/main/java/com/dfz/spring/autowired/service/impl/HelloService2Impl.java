package com.dfz.spring.autowired.service.impl;

import com.dfz.spring.autowired.service.HelloService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @version V1.0
 * @author: DFZ
 * @description: helloservice2
 * @date: 2020/4/27 09:35
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Service
@Qualifier("abc")
public class HelloService2Impl implements HelloService {

    @Override
    public String toString() {
        return "HelloService2Impl";
    }
}
