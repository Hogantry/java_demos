package com.dfz.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;

/**
 * @ClassName ThriftAdaptiveExt
 * @Description TODO
 * @Author dfz
 * @Date 2019-06-28 13:21
 * @Version 1.0
 **/
//@Adaptive
public class ThriftAdaptiveExt implements AdaptiveExt {
    @Override
    public String echo(String msg, URL url) {
        return "thrift";
    }
}
