package com.dfz.dubbo.provider.impl;

import com.alibaba.dubbo.rpc.RpcContext;
import com.dfz.dubbo.provider.api.IDemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DemoServiceImpl
 * @Description DemoServiceImpl
 * @Author dfz
 * @Date 2019-09-27 13:47
 * @Version 1.0
 **/
public class DemoServiceImpl implements IDemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
