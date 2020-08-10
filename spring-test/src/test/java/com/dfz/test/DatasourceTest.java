package com.dfz.test;

import com.dfz.test.config.DataSource;
import com.dfz.test.config.DevDatasource;
import com.dfz.test.config.ProdDatasource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @version V1.0
 * @author: DFZ
 * @description: test datasource
 * @date: 2020/4/17 13:58
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("prod")
@ContextConfiguration(classes = {SpringMain.class})
public class DatasourceTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void testSpringProfiles() {
        Assertions.assertTrue(dataSource instanceof ProdDatasource);
    }


}
