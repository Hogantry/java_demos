package com.dfz.send.httprequest2;

import com.dfz.send.httprequest.HttpClientApplication;
import com.dfz.send.httprequest.OkHttpApplication;

import java.io.IOException;

/**
 * @version V1.0
 * @author: DFZ
 * @description: HttpClient
 * @date: 2020/7/21 10:34
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class HttpClientMain {

    public static void main(String[] args) throws IOException {
//        HttpClientApplication.get();
        OkHttpApplication.get();
    }

}
