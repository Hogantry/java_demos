package com.dfz.dubbo.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * @ClassName WithOrderTwoActivateExt
 * @Description TODO
 * @Author dfz
 * @Date 2019-06-28 14:55
 * @Version 1.0
 **/
@Activate(order = 2, group = {"order"})
public class WithOrderTwoActivateExt implements ActivateExt {
    @Override
    public String echo(String msg) {
        return "WithOrderTwoActivateExt: " + msg;
    }
}
