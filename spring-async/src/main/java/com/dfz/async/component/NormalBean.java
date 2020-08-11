package com.dfz.async.component;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @version V1.0
 * @author: DFZ
 * @description: normal bean
 * @date: 2020/8/11 13:30
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class NormalBean {

    @Resource
    private AsyncBean asyncBean;

    public void call() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        asyncBean.runAsync(countDownLatch);
        asyncBean.runAsyncA(countDownLatch);
        asyncBean.runAsyncB(countDownLatch);
        asyncBean.runDfzAsync();
        countDownLatch.await();
        System.out.println("done");
    }

}
