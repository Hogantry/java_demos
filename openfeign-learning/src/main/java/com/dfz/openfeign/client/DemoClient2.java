package com.dfz.openfeign.client;

import feign.Param;
import feign.RequestLine;

/**
 * @version V1.0
 * @author: DFZ
 * @description: demo client2
 * @date: 2020/10/28 09:41
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public abstract class DemoClient2 {

    @RequestLine("GET /local-date/date?date={date}")
    abstract String getDate(@Param("date") String date);

}
