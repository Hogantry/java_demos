package com.dfz.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author DFZ
 * @Date 2021-09-07 17:08
 * @Description
 */
// 执行预热，不会记录进测试结果，给予JVM的JIT执行优化的机会
// 如下配置含义：每秒执行1次，共执行5次
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
// 真正记录到测试结果的实际执行，配置项与Warmup一致
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class JMHSample_01_HelloWorld {

    private static List<Demo> demoList;

    static {
        demoList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            demoList.add(new Demo(i, "test"));
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHSample_01_HelloWorld.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(options).run();
    }

    // 用来标记测试方法的，只有被这个注解标记的话，该方法才会参与基准测试，但是有一个基本的原则就是被@Benchmark标记的方法必须是public的
    // 类似Junit的 @Test 注解
    @Benchmark
    // 表示测量的维度，有以下维度可供选择：
    // 1. Mode.Throughput 吞吐量维度
    // 2. Mode.AverageTime 平均时间
    // 3. Mode.SampleTime 抽样检测
    // 4. Mode.SingleShotTime 检测一次调用
    // 5. Mode.All 运用所有的检测模式 在方法级别指定@BenchmarkMode的时候可以一定指定多个纬度
    @BenchmarkMode(Mode.AverageTime)
    // 代表测量的单位，比如秒级别，毫秒级别，微妙级别
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testHashMapWithoutSize() {
        HashMap<Integer, String> map = new HashMap<>();
        for (Demo demo : demoList) {
            map.put(demo.id, demo.name);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void testHashMap() {
        HashMap<Integer, String> map = new HashMap<>((int) (demoList.size() / 0.75) + 1);
        for (Demo demo : demoList) {
            map.put(demo.id, demo.name);
        }
    }

    static class Demo {

        private int id;
        private String name;

        public Demo(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

}































