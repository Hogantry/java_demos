package com.dfz.archaius;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.configuration.reloading.ReloadingStrategy;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @author: DFZ
 * @description: apache common configuration 1.X
 *               重点关注{@link PropertiesConfiguration}类，类内部有{@link ReloadingStrategy}类型的属性，每次访问属性时，会根据该属性
 *               去判断是否需要重新加载配置源，来实现属性的动态加载，即热部署功能。
 *               {@link CompositeConfiguration}可以聚合多个属性源，对外提供统一的访问接口，可自定义他们的访问顺序。
 * @date: 2020/9/18 16:18
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class CommonConfigurationApplication {

    private int type;

    /** Stores the property name. */
    private String propertyName;

    /** Stores the property value. */
    private Object propertyValue;

    /** Stores the before update flag. */
    private boolean beforeUpdate;

    public static void main(String[] args) throws ConfigurationException, InterruptedException {
        PropertiesConfiguration configuration = new PropertiesConfiguration("1.properties");
        configuration.addConfigurationListener(e -> {
            String str = "CommonConfigurationApplication{" +
                    "type=" + e.getType() +
                    ", propertyName='" + e.getPropertyName() + '\'' +
                    ", propertyValue=" + e.getPropertyValue() +
                    ", beforeUpdate=" + e.isBeforeUpdate() +
                    '}';
            System.out.println(str);
            if (e.getType() == PropertiesConfiguration.EVENT_RELOAD) {
                System.out.println("配置文件重载...");
                configuration.getKeys().forEachRemaining(k -> {
                    System.out.println("/t " + k + "-->" + configuration.getString(k));
                });
            }

        });

        FileChangedReloadingStrategy reloadingStrategy = new FileChangedReloadingStrategy();
        reloadingStrategy.setRefreshDelay(3000L);
        configuration.setReloadingStrategy(reloadingStrategy);
        TimeUnit.SECONDS.sleep(15);
        Properties name = configuration.getProperties("name");
    }

    @Override
    public String toString() {
        return "CommonConfigurationApplication{" +
                "type=" + type +
                ", propertyName='" + propertyName + '\'' +
                ", propertyValue=" + propertyValue +
                ", beforeUpdate=" + beforeUpdate +
                '}';
    }
}
