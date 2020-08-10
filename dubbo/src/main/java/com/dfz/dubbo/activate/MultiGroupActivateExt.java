package com.dfz.dubbo.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * @ClassName MultiGroupActivateExt
 * @Description TODO
 * @Author dfz
 * @Date 2019-06-28 14:53
 * @Version 1.0
 **/
@Activate(group = {"group1", "group2"})
public class MultiGroupActivateExt implements ActivateExt {
    @Override
    public String echo(String msg) {
        return "MultiGroupActivateExt: " + msg;
    }
}
