package com.dfz.openfeign;

import feign.Feign;
import feign.Logger;
import feign.Retryer;

/**
 * @version V1.0
 * @author: DFZ
 * @description: factory
 * @date: 2020/9/15 15:33
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public abstract class FeignClientFactory {

    static <T> T create(Class<T> clazz) {
        return Feign.builder()
                .logger(new Logger.ErrorLogger()).logLevel(Logger.Level.FULL)
                .retryer(Retryer.NEVER_RETRY)
                .decode404()
                .target(clazz, "http://localhost:8080");
    }

}
