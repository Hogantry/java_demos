package com.dfz.openfeign.client;

import feign.Param;
import feign.RequestLine;

/**
 * @version V1.0
 * @author: DFZ
 * @description: demo client
 * @date: 2020/9/15 15:05
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public interface DemoClient {

    @RequestLine("GET /local-date/date?date={date}")
    String getDate(@Param("date") String date);

}
