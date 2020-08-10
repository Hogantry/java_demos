package com.dfz.send.httprequest;

import com.dfz.send.httprequest.entity.ResponseVo;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @version V1.0
 * @author: DFZ
 * @description: RestTemplate
 * @date: 2020/7/20 16:41
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class RestTemplateApplication {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseVo response = restTemplate.getForObject("http://localhost:8080/qa/questionAndAnswerNew/appPages?type=1",
                ResponseVo.class);
        System.out.println(response);
    }

}
