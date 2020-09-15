package com.dfz.send.httprequest;

import com.dfz.send.httprequest.entity.ResponseVo;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

/**
 * @version V1.0
 * @author: DFZ
 * @description: RestTemplate
 * @date: 2020/7/20 16:41
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class RestTemplateApplication {

    public static void main(String[] args) throws IOException {
//        RestTemplate restTemplate = new RestTemplate();
////        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
//        ResponseVo response = restTemplate.getForObject("http://localhost:8080/qa/questionAndAnswerNew/appPages?type=1",
//                ResponseVo.class);
//        System.out.println(response);

        SimpleClientHttpRequestFactory clientFactory  = new SimpleClientHttpRequestFactory();

        // ConnectTimeout只有在网络正常的情况下才有效，因此两个一般都设置
        clientFactory.setConnectTimeout(5000); //建立连接的超时时间  5秒
        clientFactory.setReadTimeout(5000); // 传递数据的超时时间（在网络抖动的情况下，这个参数很有用）

        ClientHttpRequest client = clientFactory.createRequest(URI.create("https://www.baidu.com"), HttpMethod.GET);
        client.getBody();
        // 发送请求
        ClientHttpResponse response = client.execute();

    }

}
