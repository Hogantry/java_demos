package com.dfz.resolvable.type;

import com.dfz.resolvable.type.child.Parent;
import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SpringResolvableTypeTest
 * @Description TODO
 * @Author dfz
 * @Date 2019-07-08 11:21
 * @Version 1.0
 **/
public class SpringResolvableTypeTest {

    private List<String> listString;
    private List<List<String>> listLists;
    private Map<String, Long> maps;
    private Parent<String> parent;

    public Map<String, Long> getMaps() {
        return maps;
    }

    /**
     * private Parent<String> parent;
     * parent type:com.wangji.demo.Parent<java.lang.String>
     * 泛型参数为：class java.lang.String
     */
    public static void doTestFindParent() {
        ResolvableType parentResolvableType = ResolvableType.forField(ReflectionUtils.findField(
                SpringResolvableTypeTest.class, "parent"));
        System.out.println("parent type:" + parentResolvableType.getType());

        //获取第0个位置的参数泛型
        Class<?> resolve = parentResolvableType.getGeneric(0).resolve();
        System.out.println("泛型参数为：" + resolve);

    }

    /**
     * private List<String> listString;
     * listString type:java.util.List<java.lang.String>
     * 泛型参数为：class java.lang.String
     */
    public static void doTestFindListStr() {
        ResolvableType listStringResolvableType = ResolvableType.forField(ReflectionUtils.findField(
                SpringResolvableTypeTest.class, "listString"));
        System.out.println("listString type:" + listStringResolvableType.getType());

        //获取第0个位置的参数泛型
        Class<?> resolve = listStringResolvableType.getGeneric(0).resolve();
        System.out.println("泛型参数为：" + resolve);

    }

    /**
     * private List<List<String>> listLists;
     * listLists type:java.util.List<java.util.List<java.lang.String>>
     * 泛型参数为：interface java.util.List
     * 泛型参数为：class java.lang.String
     * 泛型参数为：class java.lang.String
     * begin 遍历
     * 泛型参数为：java.util.List<java.lang.String>
     * end 遍历
     */
    public static void doTestFindlistLists() {
        ResolvableType listListsResolvableType = ResolvableType.forField(ReflectionUtils.findField(
                SpringResolvableTypeTest.class, "listLists"));
        System.out.println("listLists type:" + listListsResolvableType.getType());

        //获取第0个位置的参数泛型
        Class<?> resolve = listListsResolvableType.getGeneric(0).resolve();
        System.out.println("泛型参数为：" + resolve);

        //region 这两种实现方式一样的 泛型参数为：class java.lang.String
        resolve = listListsResolvableType.getGeneric(0).getGeneric(0).resolve();
        System.out.println("泛型参数为：" + resolve);

        resolve = listListsResolvableType.getGeneric(0, 0).resolve();
        System.out.println("泛型参数为：" + resolve);
        //endregion

        ResolvableType[] resolvableTypes = listListsResolvableType.getGenerics();
        System.out.println("begin 遍历");
        for (ResolvableType resolvableType : resolvableTypes) {
            resolve = resolvableType.resolve();
            System.out.println("泛型参数为：" + resolve);
        }
        System.out.println("end 遍历");
//        2018-06-26 10:02:57,538  INFO [SpringResolvableTypeTest.java:100] : listLists type:java.util.List<java.util.List<java.lang.String>>
//        2018-06-26 10:02:57,538  INFO [SpringResolvableTypeTest.java:104] : 泛型参数为：interface java.util.List
//        2018-06-26 10:02:57,538  INFO [SpringResolvableTypeTest.java:107] : 泛型参数为：class java.lang.String
//        2018-06-26 10:02:57,538  INFO [SpringResolvableTypeTest.java:110] : 泛型参数为：class java.lang.String
//        2018-06-26 10:02:57,538  INFO [SpringResolvableTypeTest.java:113] : begin 遍历
//        2018-06-26 10:02:57,538  INFO [SpringResolvableTypeTest.java:116] : 泛型参数为：interface java.util.List
//        2018-06-26 10:02:57,538  INFO [SpringResolvableTypeTest.java:118] : end 遍历

    }

    /**
     * private Map<String, Long> maps;
     */
    public static void doTestFindMaps() {
        ResolvableType mapsResolvableType = ResolvableType.forField(ReflectionUtils.findField(
                SpringResolvableTypeTest.class, "maps"));
        System.out.println("maps type:" + mapsResolvableType.getType());

        System.out.println("begin 遍历");
        ResolvableType[] resolvableTypes = mapsResolvableType.getGenerics();
        Class<?> resolve = null;
        for (ResolvableType resolvableType : resolvableTypes) {
            resolve = resolvableType.resolve();
            System.out.println("泛型参数为：" + resolve);
        }
        System.out.println("end 遍历");
//        2018-06-26 10:11:51,833  INFO [SpringResolvableTypeTest.java:144] : maps type:java.util.Map<java.lang.String, java.lang.Long>
//        2018-06-26 10:11:51,833  INFO [SpringResolvableTypeTest.java:146] : begin 遍历
//        2018-06-26 10:11:51,833  INFO [SpringResolvableTypeTest.java:151] : 泛型参数为：class java.lang.String
//        2018-06-26 10:11:51,833  INFO [SpringResolvableTypeTest.java:151] : 泛型参数为：class java.lang.Long
//        2018-06-26 10:11:51,833  INFO [SpringResolvableTypeTest.java:153] : end 遍历

    }

    /**
     * Map<String, Long>
     */
    public static void doTestFindReturn() {
        // Spring的提供工具类,用于方法的返回值的泛型信息
        ResolvableType resolvableType = ResolvableType.forMethodReturnType(ReflectionUtils.findMethod(SpringResolvableTypeTest.class, "getMaps"));
        System.out.println("maps type:" + resolvableType.getType());
        System.out.println("begin 遍历");
        ResolvableType[] resolvableTypes = resolvableType.getGenerics();
        Class<?> resolve = null;
        for (ResolvableType resolvableTypeItem : resolvableTypes) {
            resolve = resolvableTypeItem.resolve();
            System.out.println("泛型参数为：" + resolve);
        }
        System.out.println("end 遍历");
//        2018-06-26 10:18:49,613  INFO [SpringResolvableTypeTest.java:134] : maps type:java.util.Map<java.lang.String, java.lang.Long>
//        2018-06-26 10:18:49,613  INFO [SpringResolvableTypeTest.java:135] : begin 遍历
//        2018-06-26 10:18:49,613  INFO [SpringResolvableTypeTest.java:140] : 泛型参数为：class java.lang.String
//        2018-06-26 10:18:49,613  INFO [SpringResolvableTypeTest.java:140] : 泛型参数为：class java.lang.Long
//        2018-06-26 10:18:49,613  INFO [SpringResolvableTypeTest.java:142] : end 遍历

    }

    /**
     * 总结一句话就是使用起来非常的简单方便，更多超级复杂的可以参考spring 源码中的测试用例：ResolvableTypeTests
     * 其实这些的使用都是在Java的基础上进行使用的哦！
     * Type是Java 编程语言中所有类型的公共高级接口（官方解释），也就是Java中所有类型的“爹”；其中，“所有类型”的描述尤为值得关注。它并不是我们平常工作中经常使用的 int、String、List、Map等数据类型，
     * 而是从Java语言角度来说，对基本类型、引用类型向上的抽象；
     * Type体系中类型的包括：原始类型(raw types 对应 Class)、参数化类型(ParameterizedType)、数组类型(GenericArrayType)、类型变量(TypeVariable)、基本类型(primitive types 对应 Class);
     * 原始类型，不仅仅包含我们平常所指的类，还包括枚举、数组、注解等；
     * 参数化类型，就是我们平常所用到的泛型List、Map；
     * 数组类型，并不是我们工作中所使用的数组String[] 、byte[]，而是带有泛型的数组，即T[] ；
     * 基本类型，也就是我们所说的java的基本类型，即int,float,double等
     *
     * @param args
     */
    public static void main(String[] args) {
        doTestFindParent();
        doTestFindListStr();
        doTestFindlistLists();
        doTestFindMaps();
        doTestFindReturn();
    }
}
