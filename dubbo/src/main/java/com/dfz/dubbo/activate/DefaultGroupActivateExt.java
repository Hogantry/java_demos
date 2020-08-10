package com.dfz.dubbo.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * @ClassName DefaultGroupActivateExt
 * @Description TODO
 * @Author dfz
 * @Date 2019-06-28 14:51
 * @Version 1.0
 **/
@Activate(group = {"default_group"})
public class DefaultGroupActivateExt implements ActivateExt {
    @Override
    public String echo(String msg) {
        return "DefaultGroupActivateExt: " + msg;
    }
}
