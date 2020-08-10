package com.dfz.send.httprequest;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @version V1.0
 * @author: DFZ
 * @description: HttpClient 使用HttpClient实现异步请求，需要 httpasyncclient 库提供支持
 * @date: 2020/7/20 10:31
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class HttpClientApplication {

    public static void main(String[] args) throws IOException {
//        get();
        post();
    }

    public static void get() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://localhost:8080/qa/questionAndAnswerNew/appPages?type=1");
        // 配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)
                // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)
                // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(true).build();

        // 将上面的配置信息 运用到这个Get请求里
        httpGet.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            System.out.println("响应状态为:" + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println("响应内容长度为:" + entity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(entity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpClient.close();
    }

    public static void post() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://localhost:8080/qa/questionAndAnswerNew/appPages");
        // 配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)
                // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)
                // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(true).build();

        // 将上面的配置信息 运用到这个Get请求里
        httpPost.setConfig(requestConfig);
        StringEntity stringEntity = new StringEntity("type=1", StandardCharsets.UTF_8);
        BasicHeader header = new BasicHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader(header);
        httpPost.setEntity(stringEntity);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            System.out.println("响应状态为:" + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println("响应内容长度为:" + entity.getContentLength());
                System.out.println("响应内容为:" + EntityUtils.toString(entity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpClient.close();
    }

}
