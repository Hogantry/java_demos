package com.dfz.ribbon.learning;

import com.netflix.client.ClientRequest;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class MyClientTest {

    @Test
    void executeTest() throws Exception {
        DefaultClientConfigImpl dfz = DefaultClientConfigImpl.getClientConfigWithDefaultValues("DFZ");
        MyClient myClient = new MyClient();
        ClientRequest clientRequest = new ClientRequest(new URI("www.baidu.com"));
        MyResponse response = myClient.execute(clientRequest, null);
        assertEquals("ResponseBody", response.getPayload());
    }

    @Test
    void configKeyTest() {
        IClientConfigKey iClientConfigKey1 = CommonClientConfigKey.valueOf("DFZ");
        IClientConfigKey iClientConfigKey2 = CommonClientConfigKey.valueOf("DFZ");
        System.out.println(iClientConfigKey1 == iClientConfigKey2);
        IClientConfigKey iClientConfigKey10 = CommonClientConfigKey.valueOf("UseIPAddrForServer");
        IClientConfigKey iClientConfigKey20 = CommonClientConfigKey.valueOf("listOfServers");
        System.out.println(iClientConfigKey10 == iClientConfigKey20);
        System.out.println(iClientConfigKey10.type());
    }

    @Test
    void buildConfigTest() {
        DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("DFZ");
        System.out.println(config.getClientName());
        System.out.println(config.get(CommonClientConfigKey.ConnectTimeout));
    }
}