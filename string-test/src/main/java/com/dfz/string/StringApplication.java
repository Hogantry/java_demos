package com.dfz.string;

import java.lang.reflect.Field;
import java.math.BigInteger;

/**
 * @version V1.0
 * @author: DFZ
 * @description: 通过反射的方式是可以修改String类的，更是可以修改运行时字符串常量池的值，一改全改。
 * @date: 2020/6/10 16:22
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class StringApplication {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        StringTable.modify();
    }

    static class StringTable {
        public static String a = "abc";
        public static String b = "abc";

        public static void modify() throws NoSuchFieldException, IllegalAccessException {
            Field field = a.getClass().getDeclaredField("value");
            field.setAccessible(true);
            char value[] = (char[]) field.get(a);
            for (int i = 0; i < value.length; i++) {
                System.out.println(value[i]);
            }
            System.out.println("----------");
            value[1] = 'd';
            for (int i = 0; i < value.length; i++) {
                System.out.println(value[i]);
            }
            System.out.println(a);
            System.out.println(b);
        }

    }

}
