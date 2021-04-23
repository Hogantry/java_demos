package com.dfz.stream;

import java.util.stream.IntStream;

/**
 * @version V1.0
 * @author: DFZ
 * @description:
 * @date: 2021/3/25 16:21
 * @Copyright: 2021 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class StreamMain {

    public static void main(String[] args) {
        IntStream intStream = IntStream.of(1, 2, 3, 4, 5);
        IntStream intStream1 = intStream.filter(i -> i > 4);
        System.out.println(intStream);
        System.out.println(intStream1);

    }

}
