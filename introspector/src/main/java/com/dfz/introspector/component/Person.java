package com.dfz.introspector.component;

/**
 * @version V1.0
 * @author: DFZ
 * @description: 内省获取到的属性名，只与Setter和Getter方法的方法名有关，与类中的字段无关
 *               方法必须是以get/set/is开头的非静态方法，其中：
 *               get/is开头的方法，必须是无参且有返回值的方法
 *               set开头的方法，必须是只有一个参数且无返回值的方法
 *
 *               属性名就是方法名去掉get/set/is，截取剩下的字符串。如果字符串的前两个字符，为Xx格式，则属性名为将首字符小写后的字符串，否则就为原字符串
 *               xxx -> xxx
 *               XXx -> XXx
 *               xXx -> xXx
 *               Xxx -> xxx
 * @date: 2020/8/12 09:52
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class Person {

    private String address;
    private boolean isCool;
    private boolean rich;
    private String Name;
    private String aGe;
    private String BIrthday;
    private Car car;

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public boolean isCool() {
        return isCool;
    }

    public void setCool(boolean cool) {
        isCool = cool;
    }

    public boolean getRich() {
        return rich;
    }

    public void setRich(boolean rich) {
        this.rich = rich;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getaGe() {
        return aGe;
    }

    public void setaGe(String aGe) {
        this.aGe = aGe;
    }

    public String getBIrthday() {
        return BIrthday;
    }

    public void setBIrthday(String BIrthday) {
        this.BIrthday = BIrthday;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String setAbc(String BIrthday) {
        this.BIrthday = BIrthday;
        return "";
    }

    public void getDfz(String BIrthday) {
        this.BIrthday = BIrthday;
    }

    public String getDfz2(String BIrthday) {
        this.BIrthday = BIrthday;
        return "";
    }

    public void isDef() {

    }

    public String isDef(String BIrthday) {
        return "";
    }

    public void setxyz(String BIrthday, String dfz) {
        this.BIrthday = BIrthday;
    }

    public static void setxyz2(String BIrthday) {

    }

    public final void setxyz3(String BIrthday) {

    }
}
