package com.dfz.dubbo.activate;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @ClassName ActivateExt
 * @Description TODO
 * @Author dfz
 * @Date 2019-06-28 14:49
 * @Version 1.0
 **/
@SPI
public interface ActivateExt {

    String echo(String msg);

}
