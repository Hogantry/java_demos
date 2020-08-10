package com.dfz.send.httprequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @version V1.0
 * @author: DFZ
 * @description: URLConnection
 * @date: 2020/7/20 09:30
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class URLConnectonApplication {

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://localhost:8080/qa/questionAndAnswerNew/appPages");
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(30000);
        // 向链接中写入数据，默认值为false，例如get请求就不需要写入数据，请求参数直接在URL中
        httpURLConnection.setDoOutput(true);
        // 从链接中获取数据，默认值为true，需要从请求中获取响应信息。
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestMethod("POST");
        // 方法内部会自动调用 httpUrlConnection.connect();不需要再手动调用，该方法会建立TCP链接
        OutputStream outStrm = httpURLConnection.getOutputStream();
        outStrm.write("type=1".getBytes());
        // http请求的body体已经生成，数据缓存在内存缓冲池中
        outStrm.close();
        byte[] bytes = new byte[100];
        int read;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 调用HttpURLConnection连接对象的getInputStream()函数,
        // 将内存缓冲区中封装好的完整的HTTP请求报文发送到服务端。
        // <=== 注意，实际发送HTTP请求就是从这块代码段开始的
        InputStream inputStream = httpURLConnection.getInputStream();
        while ((read = inputStream.read(bytes)) > -1) {
           outputStream.write(bytes, 0 , read);
        }
        String s = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
        System.out.println(s);
    }

}
