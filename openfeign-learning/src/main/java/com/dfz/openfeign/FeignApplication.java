package com.dfz.openfeign;

import com.dfz.openfeign.client.DemoClient;
import com.dfz.openfeign.client.DemoClient2;
import com.dfz.openfeign.client.ParamClient;
import com.dfz.openfeign.client.RequestLineClient;
import feign.Feign;
import feign.Request;
import feign.template.QueryTemplate;
import feign.template.UriTemplate;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @author: DFZ
 * @description: feign
 * @date: 2020/9/15 15:04
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class FeignApplication {

    public static void main(String[] args) {
        DemoClient demoClient = Feign.builder().target(DemoClient.class, "http://localhost:8080");
        demoClient.getDate("123", new Request.Options(20, TimeUnit.SECONDS, 70, TimeUnit.SECONDS, true));
//        DemoClient2 demoClient2 = Feign.builder().target(DemoClient2.class, "http://localhost:8080");
//        String result = demoClient.getDate("2020-08-01");
//        System.out.println(result);

//        feignFirstStep();
//        testParam();
//        testTemplate();

    }

    /**
     * 测试@Param注解，及其编码
     */
    private static void testParam() {
        ParamClient client = FeignClientFactory.create(ParamClient.class);
        client.testParam(new String[]{"YourBatman", "fsx"});
        System.err.println(" ------------------ ");
        client.testParam2(Arrays.asList("1", "2", "3"));
        System.err.println(" ------------------ ");

        client.testParam3("/?YourBatman/");
        System.err.println(" ------------------ ");

    }

    /**
     * 测试feign框架中的Template相关类
     */
    private static void testTemplate() {
//        UriTemplate uriTemplate = UriTemplate.create("/local-date/{date}?date={date}", StandardCharsets.UTF_8);
//        System.out.println(uriTemplate.toString());
//        HashMap<String, String> map = new HashMap<>();
//        map.put("date", "2020-20-20");
//        String expand = uriTemplate.expand(map);
//        System.out.println(expand);

        QueryTemplate queryTemplate = QueryTemplate.create("{name}", Arrays.asList("{me}", "{her}"),
                StandardCharsets.UTF_8);
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "dfz");
        map.put("me", "dfz");
        map.put("her", "lhr");
        String expand = queryTemplate.expand(map);
        System.out.println(expand);


    }

    /**
     * 初步熟悉feign
     */
    private static void feignFirstStep() {
        RequestLineClient client = FeignClientFactory.create(RequestLineClient.class);
        client.testRequestLine("2020-08-09");
        System.err.println(" ------------------ ");
        client.testRequestLine2("2020-08-09");
        System.err.println(" ------------------ ");

        // 使用Map一次传多个请求参数
        Map<String, Object> map = new HashMap<>();
        map.put("date", "2020-08-09");
        map.put("age", Arrays.asList(16, 18, 20));
        client.testRequestLine3(map);
        System.err.println(" ------------------ ");

        try {
            client.testRequestLine4("2020-08-09");
        } catch (Exception e) {
        }
        System.err.println(" ------------------ ");

        try {
            client.testRequestLine5("2020-08-09");
        } catch (Exception e) {
        }
        System.err.println(" ------------------ ");


        try {
            client.testRequestLine8("2020-08-09", 18);
        } catch (Exception e) {
        }
    }


}
