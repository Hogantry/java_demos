package com.dfz.ribbon.learning;

import com.netflix.client.ClientException;
import com.netflix.client.IResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @version V1.0
 * @author: DFZ
 * @description: ribbon response
 * @date: 2020/4/30 10:51
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class MyResponse implements IResponse {

    private URI requestUri;

    public MyResponse(URI requestUri) {
        this.requestUri = requestUri;
    }

    @Override
    public Object getPayload() throws ClientException {
        return "ResponseBody";
    }

    @Override
    public boolean hasPayload() {
        return true;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public URI getRequestedURI() {
        return requestUri;
    }

    @Override
    public Map<String, ?> getHeaders() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
