package com.dfz.dubbo.adaptive;


import org.apache.dubbo.common.URL;

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
