package com.dfz.test.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @author: DFZ
 * @description: DevDatasource
 * @date: 2020/4/17 13:55
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Component
@Profile("dev")
public class DevDatasource implements DataSource {
    @Override
    public void setUp() {
        System.out.println("开发环境");
    }
}
