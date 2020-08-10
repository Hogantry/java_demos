package com.dfz.staticfield;

/**
 * @version V1.0
 * @author: DFZ
 * @description: 在获取HungrySingleton.a的时候，先打印了init，再打印的1，说明：在类的初始化过程中，已经可以实例化该类了。
 * @date: 2020/6/8 15:17
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class StaticFieldApplication {

    public static void main(String[] args) {
        System.out.println(HungrySingleton.a);
    }

    static class HungrySingleton {

        public static int a = 1;

        /**
         * 私有静态实例引用，创建私有静态实例，并将引用所指向的实例
         */
        private static HungrySingleton singleton = new HungrySingleton();

        /**
         * 私有的构造方法
         */
        private HungrySingleton() {
            System.out.println("init");
        }

        /**
         * 返回静态实例的静态公有方法，静态工厂方法
         *
         * @return
         */
        public static HungrySingleton getSingleton() {
            return singleton;
        }

    }

}
