package com.dfz.dubbo.adaptive;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

/**
 * @ClassName AdaptiveExt
 * @Description TODO
 * @Author dfz
 * @Date 2019-06-28 11:14
 * @Version 1.0
 **/
@SPI("dubbo")
public interface AdaptiveExt {

    @Adaptive({"t"})
    String echo(String msg, URL url);

}
