package com.dfz.complete.future;

import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @author: DFZ
 * @description: CompletableFuture
 * @date: 2020/4/21 14:37
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class Main {

    public static void main(String[] args) {

        CompletableFuture<String> orderAirplane = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询航班");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("订购航班");
            return "航班信息";
        });

        CompletableFuture<String> orderHotel = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询酒店");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("订购酒店");
            return "酒店信息";
        });

        CompletableFuture<String> hireCar = orderHotel.thenCombine(orderAirplane,
                (airplane, hotel) -> {
                    System.out.println("根据航班加酒店订购租车服务");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "租车信息";
                });

        System.out.println(hireCar.join());
    }

}
