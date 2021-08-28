package com.dfz.serializable;

import java.io.*;
import java.util.ArrayList;

/**
 * 只有实现了{@link Serializable}接口的类才能被序列化，如果父类实现了该接口，则继承该父类的所有子类都可被序列化。反之，如果子类实现了Serializable
 * 接口，但父类未实现，则父类不可被序列化，且子类被序列化的时候不会序列化父类属性。反序列化时，由于父类没有实现序列化接口，所以会调用父类的
 * 构造函数，初始化父类属性。
 *
 * 子类必须也指定serialVersionUID字段，避免出现修改字段导致反序列化异常。
 *
 * 序列化 (Serialization)是将对象的状态信息转换为可以存储或传输的形式的过程。其实就是把对象保存起来，反序列化就是把这个过程反过来，直接将序列化
 * 时生成的对象状态信息映射到内存中，不会调用类的构造方法来实例化对象。
 *
 * 实现{@link Serializable}接口的类，必须指定serialVersionUID，这样在出现类信息变更时，可以向下兼容。
 * 删除字段，在反序列化时会忽略该字段；新加字段，在反序列化时会默认初始化该字段为零值。
 *
 * 类A中如果存在类B类型的字段，则类B也必须实现{@link Serializable}接口，否则类A序列化时会失败。
 *
 * 使用 transient 修饰的字段不会参与序列化和反序列化的过程，反序列化后得到的对象的该字段会被初始化为零值
 *
 * 参考{@link Person}类，可以重写 writeObject/readObject 方法，覆盖默认的序列化方式。这两个方法必须是private void的，否则不起作用。
 * 两个方法内部读写字段的顺序必须保持一致，否则会导致序列化/反序列化失败
 *
 * tips: {@link ArrayList}中的 elementData 属性就有 transient 修饰，{@link ArrayList}序列化不处理内部的元素？
 * 因为 elementData 是数组，而{@link ArrayList}内部的元素可能并未将该数组填满，如果直接序列化该数组，反序列化时可能存在内存浪费。
 * {@link ArrayList}重写了writeObject/readObject 方法，内部会保存 elementData 数组中实际有效的值。
 *
 * @ClassName Main
 * @Description Main
 * @Author dfz
 * @Date 2019-11-07 09:11
 * @Version 1.0
 **/
public class Main {

    public static void main(String[] args) {

//        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("foo2.obj"))) {
//            Foo2 foo2 = new Foo2();
//            foo2.setFooName("fooName");
//            foo2.setFoo1Name("foo1Name");
//            foo2.setFoo2Name("foo2Name");
//            outputStream.writeObject(foo2);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("------------------- Foo2");
//
//        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("foo2.obj"))) {
//            Foo2 foo2 = (Foo2)inputStream.readObject();
//            System.out.println(foo2);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("------------------- Bar2");
//
//        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("bar2.obj"))) {
//            Bar2 bar2 = new Bar2();
//            bar2.setBarName("barName");
//            bar2.setBar1Name("bar1Name");
//            bar2.setBar2Name("bar2Name");
//            outputStream.writeObject(bar2);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        System.out.println("-------------------");
//
//        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("bar2.obj"))) {
//            Bar2 bar2 = (Bar2)inputStream.readObject();
//            System.out.println(bar2);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        System.out.println("------------------- person");

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("person.obj"))) {
            Person person = new Person();
            person.setName("dfz");
            person.setAge(18);
            outputStream.writeObject(person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("-------------------");

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("person.obj"))) {
            Person Person = (Person)inputStream.readObject();
            System.out.println(Person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
