package com.dfz.openfeign.client;

import feign.*;

import java.util.Map;

/**
 * @version V1.0
 * @author: DFZ
 * @description: test
 * @date: 2020/9/15 15:36
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public interface RequestLineClient {

    /**
     * 1、正常使用、正常书写
     *
     * @param date
     * @return
     */
    @Headers({"Accept:*/*", "Accept-Language:    zh-cn"})
    @RequestLine("GET /local-date/date?date={date}")
    String testRequestLine(@Param("date") String date);

    /**
     * 2、GET后不止一个空格，有多个空格
     * @param date
     * @return
     */
    @RequestLine("GET             /local-date/date?date={date}")
    String testRequestLine2(@Param("date") String date);

    /**
     * 3、使用Map一次性传递多个查询参数，使用注解为@QueryMap
     * 注意： @QueryMap后的参数必须是Map类型
     * 如果@QueryMap后的参数为POJO，则必须使用BeanQueryMapEncoder，该类会将POJO转换成Map
     * @param params
     * @return
     */
    @RequestLine("GET /local-date/date")
    String testRequestLine3(@QueryMap Map<String, Object> params);

    /**
     * 4、方法参数上不使用任何注解
     * 方法参数上不使用任何注解时，会强转成POST方式提交请求，方法参数的值会作为POST的body被提交
     * @param date
     * @return
     */
    @RequestLine("GET /local-date/date")
    String testRequestLine4(String date);

    /**
     * 5、方法上标注有@Body注解，然后把方法参数传递给它
     * @param name
     * @return
     */
    @RequestLine("GET /local-date/date")
    @Body("{name}")
    String testRequestLine5(@Param("name") String name);

    // 6、方法两个参数，均不使用注解标注
    // 启动直接报错：Method has too many Body parameters:
    // @RequestLine("GET /feign/demo1")
    // String testRequestLine6(String name,Integer age);

    // 7、启动直接报错：Body parameters cannot be used with form parameters.
    // @RequestLine("GET /feign/demo1")
    // @Body("{name}")
    // String testRequestLine7(@Param("name") String name, Integer age);

    // 8、如果你既想要body参数，又想要查询参数，请这么写
    @RequestLine("GET /local-date/date?date={date}")
    @Body("{age}")
    String testRequestLine8(@Param("date") String name, @Param("age") Integer age);

}
