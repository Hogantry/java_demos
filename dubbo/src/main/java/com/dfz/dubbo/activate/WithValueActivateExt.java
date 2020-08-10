package com.dfz.dubbo.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * @ClassName WithValueActivateExt
 * @Description TODO
 * @Author dfz
 * @Date 2019-06-28 14:56
 * @Version 1.0
 **/
@Activate(value = {"value"}, group = {"value"})
public class WithValueActivateExt implements ActivateExt {
    @Override
    public String echo(String msg) {
        return "WithValueActivateExt: " + msg;
    }
}
