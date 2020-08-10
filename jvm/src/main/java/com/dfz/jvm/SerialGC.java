package com.dfz.jvm;

import java.util.Random;

/**
 * @ClassName SerialGC
 * @Description  JVM参数：
 *               堆内存：-Xmx20M -Xms20M
 *               新生代：-XX:NewRatio=3      新生代:老年代 = 1:3，一共将堆分成4份
 *                     -XX:SurvivorRatio=3  survivor:eden = 1:1:3，一共将新生代分成5份
 *               GC参数：-XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 *               堆空间：20M，新生代：5M，老年代：15M，新生代eden区：3M，新生代from区：1M，新生代to区：1M
 *               -Xmx20M -Xms20M -XX:NewRatio=3 -XX:SurvivorRatio=3 -XX:+UseSerialGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 *
 * @Author dfz
 * @Date 2019-08-15 17:21
 * @Version 1.0
 **/
public class SerialGC {

    public static void main(String[] args) {
        byte[][] useMemory = new byte[1000][];
        Random random = new Random();
        for (int i = 0; i < useMemory.length; i++) {
            // 创建1M的对象
            useMemory[i] = new byte[1024 * 1024];
            // 20%的概率将创建出来的对象变为可回收对象
            if (random.nextInt(100) < 20) {
                System.out.println("created byte[] and set to null: " + i);
                useMemory[i] = null;
            } else {
                System.out.println("created byte[]: " + i);
            }
        }
    }

}
