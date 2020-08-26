package com.dfz.dubbo.provider;


import com.dfz.dubbo.provider.api.IDemoService;
import com.dfz.dubbo.provider.impl.DemoServiceImpl;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;

/**
 * @ClassName BasicProvider
 * @Description 配置 ServiceConfig 做服务导出时， ApplicationConfig、RegistryConfig、ProtocolConfig是三个必配的属性，但是在
 *              ServiceConfig 的 export方法中有对相关（包括上述三个属性）的检测，使用checkXXX方法，方法会检测当相关属性为空时，会
 *              new一个对象，并使用 appendProperties 方法从类路径的默认的dubbo.properties文件中获取对象属性。
 *
 *              如下实例，就是在dubbo.properties配置文件中配置了ApplicationConfig、RegistryConfig、ProtocolConfig是三个必配的属性。
 * @Author dfz
 * @Date 2019-09-27 13:45
 * @Version 1.0
 **/
public class BasicProvider {

    public static void main(String[] args) throws IOException {
        IDemoService demoService = new DemoServiceImpl();
        ServiceConfig<IDemoService> service = new ServiceConfig<>();
        service.setInterface(IDemoService.class);
        service.setRef(demoService);
        service.setVersion("1.0.0");
        // 暴露及注册服务
        service.export();
        System.in.read();

    }

}
