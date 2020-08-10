package com.dfz.reflection;

/**
 * @ClassName ParentInterface
 * @Description ParentInterface
 * @Author dfz
 * @Date 2019-07-12 13:26
 * @Version 1.0
 **/
public interface ParentInterface extends GrandPaInterface {

    void parentMethod();

    default void defaultMethod() {
        System.out.println("default");
    }

}
