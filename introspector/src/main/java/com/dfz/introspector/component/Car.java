package com.dfz.introspector.component;

/**
 * @ClassName Car
 * @Description Car
 * @Author dengfazhi
 * @Date 2020/8/12 8:30 下午
 * @Version 1.0
 **/
public class Car {

    private String bread;
    private String price;

    public String getBread() {
        return bread;
    }

    public void setBread(String bread) {
        this.bread = bread;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "bread='" + bread + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

}
