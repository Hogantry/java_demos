package com.dfz.dubbo.adaptive;

import org.apache.dubbo.common.URL;

/**
 * @ClassName DubboAdaptiveExt
 * @Description TODO
 * @Author dfz
 * @Date 2019-06-28 11:15
 * @Version 1.0
 **/
public class DubboAdaptiveExt implements AdaptiveExt {
    @Override
    public String echo(String msg, URL url) {
        return "dubbo";
    }
}
