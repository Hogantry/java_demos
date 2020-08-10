package com.dfz.send.httprequest;

import okhttp3.*;

import java.io.IOException;

/**
 * @version V1.0
 * @author: DFZ
 * @description: OK Http
 * @date: 2020/7/20 11:11
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class OkHttpApplication {

    public static void main(String[] args) {
//        get();
        post();
    }

    public static void get() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://localhost:8080/qa/questionAndAnswerNew/appPages?type=1";
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        // 同步发送请求
//        try {
//            Response response = call.execute();
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // 异步发送请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });
    }

    public static void post() {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestBody = "type=1";
        Request request = new Request.Builder()
                .url("http://localhost:8080/qa/questionAndAnswerNew/appPages")
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    System.out.println(headers.name(i) + ":" + headers.value(i));
                }
                System.out.println("onResponse: " + response.body().string());
                response.close();
            }
        });
    }

}
