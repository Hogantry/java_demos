package com.dfz.jol;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @Author DFZ
 * @Date 2021-06-30 18:47
 * @Description jol-core 是OpenJDK 提供的一个 jar 包，可使用他打印出Java对象内存布局信息
 *              Java对象包含三个部分：
 *                  1. 对象头：他又包含三个部分
 *                      a. mark word，32 位系统占用 4 字节，64 位系统占用 8 字节；
 *                      b. class pointer，指向当前对象在方法区中的 class 定义的首地址，32 位系统占用 4 字节，64 位系统不开启指针压缩占用 8 字节，开启指针压缩(默认开启)占用 4 字节；
 *                      c. 数组长度，数组对象才会有，因为是 int，所以占用 4 字节。
 *                  2. 实例变量，比如类里面定义了一个 int，那么这里就有 4 个字节；
 *                  3. 对齐填充：一个对象占用的总内存必须是 8 的整数倍，不足的部分以空位填充整齐。
 */
@Slf4j(topic = "synchro")
public class CommonObjectHeader {

    private static int i = 1;

    private boolean bo = false;
    private long lo = 6;
    private Object o = new Object();

    public static void main(String[] args) {
        log.debug(ClassLayout.parseInstance(new CommonObjectHeader()).toPrintable());
    }

}
