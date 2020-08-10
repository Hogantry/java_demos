package com.dfz.spring.autowired.entity;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @author: DFZ
 * @description: Person2
 * @date: 2020/6/5 09:30
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Component
@Order(3)
public class Person2 {

    public Person2() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Person2{}";
    }
}
