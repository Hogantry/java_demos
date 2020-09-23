package com.dfz.archaius;

import com.netflix.config.*;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationUtils;

import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @author: DFZ
 * @description: archaius
 * @date: 2020/9/18 15:35
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ArchaiusApplication {

    public static void main(String[] args) throws InterruptedException {
//        System.out.println(System.getProperty("archaius.configurationSource.additionalUrls"));
//        DynamicIntProperty myAge = DynamicPropertyFactory.getInstance().getIntProperty("my.age", 16);
//        System.out.println(myAge);
//        System.out.println(myAge.get());

        func2();

    }

    public static void func1() throws InterruptedException {
        DynamicURLConfiguration config = new DynamicURLConfiguration();
        config.addConfigurationListener(e -> {
            String str = "DynamicURLConfiguration{" +
                    "type=" + e.getType() +
                    ", propertyName='" + e.getPropertyName() + '\'' +
                    ", propertyValue=" + e.getPropertyValue() +
                    ", beforeUpdate=" + e.isBeforeUpdate() +
                    '}';
            System.out.println(str);
        });

        while (true) {
//            ConfigurationUtils.dump(config, System.out);
//            System.out.println();
//            TimeUnit.SECONDS.sleep(10);
        }
    }

    public static void func2() {
        AbstractConfiguration configInstance = ConfigurationManager.getConfigInstance();
        System.out.println(configInstance.getString("shabi"));
        DynamicPropertyFactory.getInstance();
        System.setProperty("shabi", "shabi");
//        System.out.println(configInstance.getString("shabi"));

    }
    public static void func3() throws InterruptedException {
        DynamicPropertyFactory propertyFactory = DynamicPropertyFactory.getInstance();
        DynamicStringProperty nameProperty = propertyFactory.getStringProperty("my.name", "defaultName");
        nameProperty.addCallback(() -> System.out.println("name属性值发生变化："));

        // 10秒钟读一次
        while (true) {
            System.out.println(nameProperty.get());
            TimeUnit.SECONDS.sleep(50);
        }
    }



}
