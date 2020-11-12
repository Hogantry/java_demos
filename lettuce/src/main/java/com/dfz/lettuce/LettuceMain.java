package com.dfz.lettuce;

import io.lettuce.core.RedisURI;

/**
 * @version V1.0
 * @author: DFZ
 * @description: lettuce
 * @date: 2020/4/16 09:26
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class LettuceMain {

    public static void main(String[] args) {
        RedisURI redisURI = RedisURI.create("redis://localhost");
//        RedisURI uri = RedisURI.builder().withHost("localhost").withPort(6379).build();
    }

}
