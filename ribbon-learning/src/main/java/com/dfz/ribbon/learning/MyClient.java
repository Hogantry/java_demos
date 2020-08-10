package com.dfz.ribbon.learning;

import com.netflix.client.ClientRequest;
import com.netflix.client.IClient;
import com.netflix.client.config.IClientConfig;

/**
 * @version V1.0
 * @author: DFZ
 * @description: ribbon client
 * @date: 2020/4/30 10:50
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class MyClient implements IClient<ClientRequest, MyResponse> {
    @Override
    public MyResponse execute(ClientRequest request, IClientConfig requestConfig) throws Exception {
        return new MyResponse(request.getUri());
    }
}
