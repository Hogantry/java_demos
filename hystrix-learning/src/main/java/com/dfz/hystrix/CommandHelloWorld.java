package com.dfz.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @version V1.0
 * @author: DFZ
 * @description:
 * @date: 2020/10/19 10:06
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    /**
     * 指定一个HystrixCommandGroupKey，这样熔断策略会按照此组执行
     *
     * @param name
     */
    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("MyAppGroup"));
        this.name = name;
    }

    @Override
    protected String run() {
        if (name == null) {
            throw new NullPointerException();
        }
        return "Hello " + name + "!";
    }

    @Override
    protected String getFallback() {
        return "this is fallback msg";
    }
}
