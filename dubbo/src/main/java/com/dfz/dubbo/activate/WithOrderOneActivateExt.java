package com.dfz.dubbo.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * @ClassName WithOrderOneActivateExt
 * @Description TODO
 * @Author dfz
 * @Date 2019-06-28 14:54
 * @Version 1.0
 **/
@Activate(order = 1, group = {"order"})
public class WithOrderOneActivateExt implements ActivateExt {
    @Override
    public String echo(String msg) {
        return "WithOrderOneActivateExt: " + msg;
    }
}
