package com.dfz.spring.autowired.service.impl;

import com.dfz.spring.autowired.dao.HelloDao;
import com.dfz.spring.autowired.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @version V1.0
 * @author: DFZ
 * @description: service
 * @date: 2020/4/27 09:22
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Service
@Qualifier("abcd")
public class HelloServiceImpl implements HelloService {

    @Autowired
    private HelloDao helloDao;

    @Override
    public String toString() {
        return "HelloServiceImpl{" +
                "helloDao=" + helloDao +
                '}';
    }
}
