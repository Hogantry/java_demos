package com.dfz.classloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * @ClassName Main
 * @Description 用户自定义的ClassLoader，其默认的parent ClassLoader就是系统ClassLoader。
 *              user.dir 当前类（编译后的class文件）所在的目录（类所在包所在的目录）。
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
    public static void main(String[] args) throws IOException {

        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        File classPath = new File(WEB_ROOT);
        String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
        System.out.println("web_root: " + WEB_ROOT + ";repository: " + repository);
        urls[0] = new URL(null, repository, streamHandler);
        ClassLoader loader = new URLClassLoader(urls);
        while (loader.getParent() != null) {
            System.out.println(loader.getParent());
            loader = loader.getParent();
        }
    }

}
