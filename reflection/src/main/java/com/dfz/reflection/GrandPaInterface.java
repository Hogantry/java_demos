package com.dfz.reflection;

/**
 * @ClassName GrandPaInterface
 * @Description GrandPaInterface
 * @Author dfz
 * @Date 2019-07-12 14:51
 * @Version 1.0
 **/
public interface GrandPaInterface {

    void methodGp();

    default void defaultGpMethod() {
        System.out.println("defaultGpMethod");
    }

}
