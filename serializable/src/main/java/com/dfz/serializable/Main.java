package com.dfz.serializable;

import java.io.*;

/**
 * 只有实现了Serializable接口的类才能被序列化，如果父类实现了该接口，则继承该父类的所有子类都可被序列化。反之，如果子类实现了Serializable
 * 接口，但父类未实现，则父类不可被序列化，且子类被序列化的时候不会序列化父类属性。反序列化时，由于父类没有实现序列化接口，所以会调用父类的
 * 构造函数，初始化父类属性。
 *
 * 序列化 (Serialization)是将对象的状态信息转换为可以存储或传输的形式的过程。其实就是把对象保存起来，反序列化就是把这个过程反过来，直接将序列化
 * 时生成的对象状态信息映射到内存中，不会调用类的构造方法来实例化对象。
 *
 * @ClassName Main
 * @Description Main
 * @Author dfz
 * @Date 2019-11-07 09:11
 * @Version 1.0
 **/
public class Main {

    public static void main(String[] args) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("foo2.obj"))) {
            Foo2 foo2 = new Foo2();
            foo2.setFooName("fooName");
            foo2.setFoo1Name("foo1Name");
            foo2.setFoo2Name("foo2Name");
            outputStream.writeObject(foo2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("------------------- Foo2");

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("foo2.obj"))) {
            Foo2 foo2 = (Foo2)inputStream.readObject();
            System.out.println(foo2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("------------------- Bar2");

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("bar2.obj"))) {
            Bar2 bar2 = new Bar2();
            bar2.setBarName("barName");
            bar2.setBar1Name("bar1Name");
            bar2.setBar2Name("bar2Name");
            outputStream.writeObject(bar2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("-------------------");

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("bar2.obj"))) {
            Bar2 bar2 = (Bar2)inputStream.readObject();
            System.out.println(bar2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
