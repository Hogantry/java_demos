package com.dfz.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @version V1.0
 * @author: DFZ
 * @description: 自定义ClassLoader在调用loadClass方法时，默认是只加载本类，并不会完成链接操作。在完成加载之后，如果使用该类了，则会自动使用
 *               自定义的类加载器去完成链接初始化等操作，并自动加载关联的类。
 *
 *               在本例中HelloConfig类强依赖HelloDao类，如果在路径下删除HelloDao类的话，执行loadClass(HelloConfig)方法是不会有问题的，
 *               再次佐证了上述所说，loadClass方法只会完成加载操作，后续的链接初始化都是在使用该类时完成。
 * @date: 2020/9/15 08:43
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class MyClassLoader extends ClassLoader {

    /**
     * 指定路径
     */
    private File path;

    public MyClassLoader(String path) {
        File filePath = new File(path);
        if (!filePath.exists()) {
            throw new RuntimeException("文件目录路径不存在");
        }
        if (!filePath.isDirectory()) {
            throw new RuntimeException("不是文件目录");
        }
        this.path = filePath;
    }

    public MyClassLoader(File path) {
        if (!path.exists()) {
            throw new RuntimeException("文件目录路径不存在");
        }
        if (!path.isDirectory()) {
            throw new RuntimeException("不是文件目录");
        }
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        // 获取该class文件字节码数组
        byte[] classData = getBiteCode(name);

        if (classData != null) {
            // 将class的字节码数组转换成Class类的实例
            clazz = defineClass(name, classData, 0, classData.length);
        }
        return clazz;
    }

    /**
     * 将class文件转化为字节码数组
     *
     * @return
     */
    private byte[] getBiteCode(String name) {
        String path = name.replace(".", File.separator) + ".class";
        File classFile = new File(this.path.getAbsolutePath() + File.separator + path);
        if (classFile.exists()) {
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                in = new FileInputStream(classFile);
                out = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = in.read(buffer)) != -1) {
                    out.write(buffer, 0, size);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            return out.toByteArray();
        } else {
            return null;
        }
    }

}
