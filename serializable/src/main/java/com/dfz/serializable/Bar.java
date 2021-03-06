package com.dfz.serializable;

import java.io.Serializable;

/**
 * @ClassName Bar
 * @Description Bar
 * @Author dfz
 * @Date 2019-11-07 09:14
 * @Version 1.0
 **/
public class Bar {

    private String barName;

    public Bar() {
        System.out.println("Bar...");
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    @Override
    public String toString() {
        return "Bar{" +
                "barName='" + barName + '\'' +
                '}';
    }
}

class Bar1 extends Bar {

    private String bar1Name;

    public Bar1() {
        System.out.println("Bar1...");
    }

    public String getBar1Name() {
        return bar1Name;
    }

    public void setBar1Name(String bar1Name) {
        this.bar1Name = bar1Name;
    }

    @Override
    public String toString() {
        return "Bar1{" +
                "bar1Name='" + bar1Name + '\'' +
                '}' + "Bar{" +
                "barName='" + this.getBarName() + '\'' +
                '}';
    }
}

class Bar2 extends Bar1 implements Serializable {

    private static final long serialVersionUID = 9001647507666295131L;
//    private String bar2Name;
    private String address;
    private int age;

    public Bar2() {
        System.out.println("Bar2...");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //    public String getBar2Name() {
//        return bar2Name;
//    }
//
//    public void setBar2Name(String bar2Name) {
//        this.bar2Name = bar2Name;
//    }

    @Override
    public String toString() {
        return "Bar2{" +
//                "bar2Name='" + bar2Name + '\'' +
                "address='" + address + '\'' +
                "age='" + age + '\'' +
                '}' + "Bar1{" +
                "bar1Name='" + getBar1Name() + '\'' +
                '}' + "Bar{" +
                "barName='" + getBarName() + '\'' +
                '}';
    }
}
