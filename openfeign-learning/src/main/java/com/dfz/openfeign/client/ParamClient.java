package com.dfz.openfeign.client;

import feign.Param;
import feign.RequestLine;

import java.util.Collection;

/**
 * @version V1.0
 * @author: DFZ
 * @description: ParamClient
 * @date: 2020/10/13 15:34
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public interface ParamClient {

    /**
     * 1、参数为数组类型
     * @param names
     * @return
     */
    @RequestLine("GET /feign/demo2?name={name}")
    String testParam(@Param("name") String[] names);

    /**
     * 2、参数为List类型
     * @param names
     * @return
     */
    @RequestLine("GET /feign/demo2?name={name}")
    String testParam2(@Param("name") Collection<String> names);

    /**
     * 3、参数值包含特殊字符：? / 这种
     * @param name
     * @return
     */
    @RequestLine("GET /feign/demo2?name={name}")
    String testParam3(@Param("name") String name);

}
