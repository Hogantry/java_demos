package com.dfz.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;

/**
 * @ClassName CloudAdaptiveExt
 * @Description TODO
 * @Author dfz
 * @Date 2019-06-28 13:20
 * @Version 1.0
 **/
public class CloudAdaptiveExt implements AdaptiveExt {
    @Override
    public String echo(String msg, URL url) {
        return "cloud";
    }
}
