package com.dfz.classloader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName Main
 * @Description 用户自定义的ClassLoader，其默认的parent ClassLoader就是系统ClassLoader。继承关系如下
 *              DIY classLoader -> AppClassLoader(SystemClassLoader) -> ExtensionClassLoader -> BootstrapClassLoader
 *              user.dir 当前项目（编译后的class文件）所在的目录（类所在包所在的目录）。
 * @Author dfz
 * @Date 2019-09-02 09:15
 * @Version 1.0
 **/
public class Main {

    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator  + "webroot";

    /***
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        System.out.println(ClassLoader.getSystemClassLoader().getParent().getClass());

//        URL[] urls = new URL[1];
//        URLStreamHandler streamHandler = null;
//        File classPath = new File(WEB_ROOT);
//        String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
//        System.out.println("web_root: " + WEB_ROOT + ";repository: " + repository);
//        urls[0] = new URL(null, repository, streamHandler);
//        ClassLoader loader = new URLClassLoader(urls);
//        while (loader.getParent() != null) {
//            System.out.println(loader.getParent());
//            loader = loader.getParent();
//        }

        // 需要先编译好spring-autowired项目
//        String classPath = "/Users/dfz/J2EE/java_demos/spring-autowired/target/classes/";
//        MyClassLoader myClassLoader = new MyClassLoader(classPath);
//        String clazzName = "com.dfz.spring.autowired.config.HelloConfig";
//        Class<?> loadClass = myClassLoader.loadClass(clazzName);
//        Method helloDao = loadClass.getDeclaredMethod("helloDao");
//        Object o = loadClass.newInstance();
//        Object invoke = helloDao.invoke(o);
    }

}
