package com.dfz.ribbon.learning;

import com.google.common.base.Optional;
import com.netflix.client.ClientRequest;
import com.netflix.client.SimpleVipAddressResolver;
import com.netflix.client.VipAddressResolver;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.loadbalancer.*;
import com.netflix.stats.distribution.DataDistribution;
import com.netflix.stats.distribution.DataPublisher;

import java.net.URI;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @version V1.0
 * @author: DFZ
 * @description: Ribbon
 * @date: 2020/7/27 16:15
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class RibbonClientApplication {

    public static void main(String[] args) throws Exception {
//        buildConfigTest();
//        vipAddressResolverTest();
//        configurationBasedServerListTest();
//        dataPublishTest();
//        serverStatsTest();
//        loadBalancerStatsTest();
//        abstractServerPredicateTest();
        baseLoaderBalancerTest();
    }

    private static void testRibbonClient() throws Exception {
        DefaultClientConfigImpl dfz = DefaultClientConfigImpl.getClientConfigWithDefaultValues("DFZ");
        MyClient myClient = new MyClient();
        ClientRequest clientRequest = new ClientRequest(new URI("www.baidu.com"));
        MyResponse response = myClient.execute(clientRequest, null);
        System.out.println("ResponseBody: " + response.getPayload());
    }

    private static void configKeyTest() {
        IClientConfigKey iClientConfigKey1 = CommonClientConfigKey.valueOf("DFZ");
        IClientConfigKey iClientConfigKey2 = CommonClientConfigKey.valueOf("DFZ");
        System.out.println(iClientConfigKey1 == iClientConfigKey2);
        IClientConfigKey iClientConfigKey10 = CommonClientConfigKey.valueOf("UseIPAddrForServer");
        IClientConfigKey iClientConfigKey20 = CommonClientConfigKey.valueOf("listOfServers");
        System.out.println(iClientConfigKey10 == iClientConfigKey20);
        System.out.println(iClientConfigKey10.type());
    }

    /**
     * ribbon 配置类的默认值的设置，默认使用Archaius框架，其默认加载config.properties配置文件。至于如何加载环境变量或启动时参数，
     * 请查看Archaius框架的代码
     */
    private static void buildConfigTest() {
        DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("DFZ");
        System.out.println(config.getClientName());
        System.out.println(config.get(CommonClientConfigKey.ConnectTimeout));

        DefaultClientConfigImpl config2 = DefaultClientConfigImpl.getClientConfigWithDefaultValues("LHR");
        System.out.println(config2.getClientName());
        System.out.println(config2.get(CommonClientConfigKey.ConnectTimeout));
    }

    /**
     * 请注意：这里取值不是从IClientConfig里取的，而是从全局Configuration配置里取值的(即Archaius框架，加载config.properties配置文件)，请勿弄错。
     */
    private static void vipAddressResolverTest() {
        String vipArr = "${foo}.bar:${port},${foobar}:80,localhost:8080";
        VipAddressResolver vipAddressResolver = new SimpleVipAddressResolver();
        String vipAddredd = vipAddressResolver.resolve(vipArr, null);
        System.out.println(vipAddredd);
    }

    /**
     * 在Spring Cloud中，脱离eureka使用ribbon的经典配置：
     * # 禁用ribbon在eureka里使用
     * ribbon.eureka.enabled=false
     * # 配置服务提供者的地址
     * DFZ.ribbon.listOfServers=localhost:8080,localhost:8081
     */
    public static void configurationBasedServerListTest() {
        DefaultClientConfigImpl config = DefaultClientConfigImpl.getClientConfigWithDefaultValues("DFZ");

        ConfigurationBasedServerList serverList = new ConfigurationBasedServerList();
        serverList.initWithNiwsConfig(config);

        serverList.getInitialListOfServers().forEach(server -> {
            System.out.println(server.getId());
            System.out.println(server.getHost());
            System.out.println(server.getPort());
            System.out.println(server.getHostPort());
            System.out.println(server.getScheme());
            System.out.println("-----------------------------");
        });
    }

    public static void dataPublishTest() throws InterruptedException {
        //最大样本容量，注意是最大
        int bufferSize = 50;
        double[] percents = {50, 80, 90, 95, 99};
        DataDistribution accumulator = new DataDistribution(bufferSize, percents);
        // 生产数据
        produceValue(accumulator);
        // 发布数据
        publishData(accumulator);
        // 监控（模拟监控页面：数据打印到控制台）
        monitor(accumulator);

        // hold主线程
        TimeUnit.SECONDS.sleep(10000);
    }

    public static void serverStatsTest() throws InterruptedException {
        ServerStats serverStats = new ServerStats();
        // 缓冲区大小最大1000。 若QPS是200，5s能装满它  这个QPS已经很高了
        serverStats.setBufferSize(1000);
        // 5秒收集一次数据
        serverStats.setPublishInterval(5000);
        // 请务必调用此初始化方法
        serverStats.initialize(new Server("YourBatman", 80));

        // 多个线程持续不断的发送请求
        request(serverStats);
        // 监控ServerStats状态
        monitor(serverStats);

        // hold主线程
        TimeUnit.SECONDS.sleep(10000);
    }

    public static void loadBalancerStatsTest() throws InterruptedException {
        LoadBalancerStats lbs = new LoadBalancerStats("DFZ");

        // 添加Server
        List<Server> serverList = new ArrayList();
        serverList.add(createServer("华南", 1));
        serverList.add(createServer("华东", 1));
        serverList.add(createServer("华东", 2));

        serverList.add(createServer("华北", 1));
        serverList.add(createServer("华北", 2));
        serverList.add(createServer("华北", 3));
        serverList.add(createServer("华北", 4));
        lbs.updateServerList(serverList);

        Map<String, List<Server>> zoneServerMap = new HashMap();
        // 模拟向每个Server发送请求  记录ServerStatus数据
        serverList.forEach(server -> {
            ServerStats serverStat = lbs.getSingleServerStat(server);
            request(serverStat);

            // 顺便按照zone分组
            String zone = server.getZone();
            if (zoneServerMap.containsKey(zone)) {
                zoneServerMap.get(zone).add(server);
            } else {
                List<Server> servers = new ArrayList<>();
                servers.add(server);
                zoneServerMap.put(zone, servers);
            }
        });
        lbs.updateZoneServerMapping(zoneServerMap);
        // 从lbs里拿到一些监控数据
        monitor(lbs);

        TimeUnit.SECONDS.sleep(500);
    }

    public static void abstractServerPredicateTest() throws InterruptedException {
        // 准备一批服务器
        List<Server> serverList = new ArrayList<>();
        serverList.add(createServer("华南", 1));
        serverList.add(createServer("华东", 1));
        serverList.add(createServer("华东", 2));

        serverList.add(createServer("华北", 1));
        serverList.add(createServer("华北", 2));
        serverList.add(createServer("华北", 3));
        serverList.add(createServer("华北", 4));

        // 指定当前的zone
        // 可在配置文件 config.properties 中配置 @zone=华北
//        DeploymentContext deploymentContext = ConfigurationManager.getDeploymentContext();
//        deploymentContext.setValue(DeploymentContext.ContextKey.zone, "华北");

        // 准备断言器
        ZoneAffinityPredicate predicate = new ZoneAffinityPredicate();

        while (true) {
            // 以轮询方式选择Server
            Optional<Server> serverOptional = predicate.chooseRoundRobinAfterFiltering(serverList);
            Server server = serverOptional.get();
            String zone = server.getZone();
            System.out.println("区域：" + zone + "，序号是：" + server.getPort());

            TimeUnit.SECONDS.sleep(5);
        }
    }

    public static void baseLoaderBalancerTest() {
        List<Server> serverList = new ArrayList<>();
        serverList.add(createServer("华南", 1));
        serverList.add(createServer("华东", 1));
        serverList.add(createServer("华东", 2));

        serverList.add(createServer("华北", 1));
        serverList.add(createServer("华北", 2));
        serverList.add(createServer("华北", 3));
        serverList.add(createServer("华北", 4));

        ILoadBalancer lb = new BaseLoadBalancer();
        lb.addServers(serverList);

        // 把华北的机器都标记为down掉
//        LoadBalancerStats loadBalancerStats = ((BaseLoadBalancer) lb).getLoadBalancerStats();
//        loadBalancerStats.updateServerList(serverList); // 这一步不能省哦~~~
//        loadBalancerStats.getServerStats().keySet().forEach(server -> {
//            if (server.getHost().contains("华北")) {
//                lb.markServerDown(server);
//            }
//        });
        lb.getAllServers().forEach(server -> {
            if (server.getHost().contains("华北")) {
                lb.markServerDown(server);
            }
        });

        for (int i = 0; i < 10; i++) {
            System.out.println(lb.chooseServer(null));
        }
    }

    /**
     * 单独线程模拟刷页面，获取监控到的数据
     * @param lbs
     */
    private static void monitor(LoadBalancerStats lbs) {
        List<String> zones = Arrays.asList("华南", "华东", "华北");
        new Thread(() -> {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleWithFixedDelay(() -> {
                zones.forEach(zone -> {
                    System.out.println("区域[" + zone + "]概要：");
                    int instanceCount = lbs.getInstanceCount(zone);
                    int activeRequestsCount = lbs.getActiveRequestsCount(zone);
                    double activeRequestsPerServer = lbs.getActiveRequestsPerServer(zone);
                    ZoneSnapshot zoneSnapshot = lbs.getZoneSnapshot(zone);

                    System.out.printf("实例总数：%s，活跃请求总数：%s，平均负载：%s\n", instanceCount, activeRequestsCount, activeRequestsPerServer);
                    System.out.println(zoneSnapshot);
                });
                System.out.println("======================================================");
            }, 5, 5, TimeUnit.SECONDS);
        }).start();
    }


    /**
     * 请注意：请必须保证Server的id不一样，否则放不进去List的（因为Server的equals hashCode方法仅和id有关）
     *      * 请注意：请必须保证Server的id不一样，否则放不进去List的（因为Server的equals hashCode方法仅和id有关
     * @param zone
     * @param index
     * @return
     */
    private static Server createServer(String zone, int index) {
        Server server = new Server("www.baidu" + zone + ".com", index);
        server.setZone(zone);
        return server;
    }

    /**
     * 单独线程模拟刷页面，获取监控到的数据
     * @param serverStats
     */
    private static void monitor(ServerStats serverStats) {
        new Thread(() -> {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleWithFixedDelay(() -> {
                System.out.println("=======时间：" + serverStats.getResponseTimePercentileTime() + "，统计值如下=======");
                System.out.println("请求总数(持续累计)：" + serverStats.getTotalRequestsCount());
                System.out.println("平均响应时间：" + serverStats.getResponseTimeAvg());
                System.out.println("最小响应时间：" + serverStats.getResponseTimeMin());
                System.out.println("最大响应时间：" + serverStats.getResponseTimeMax());


                System.out.println("样本大小(取样本)：" + serverStats.getResponseTimePercentileNumValues());
                System.out.println("样本下的平均响应时间：" + serverStats.getResponseTimeAvgRecent());
                System.out.println("样本下的响应时间中位数：" + serverStats.getResponseTime50thPercentile());
                System.out.println("样本下的响应时间90分位数：" + serverStats.getResponseTime90thPercentile());
            }, 5, 5, TimeUnit.SECONDS);
        }).start();
    }


    /**
     * 模拟请求（开启5个线程，每个线程都持续不断的请求）
     */
    private static void request(ServerStats serverStats) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (true) {
                    // 请求之前 记录活跃请求数
                    serverStats.incrementActiveRequestsCount();
                    serverStats.incrementNumRequests();
                    long rt = doSomething();
                    // 请求结束， 记录响应耗时
                    serverStats.noteResponseTime(rt);
                    serverStats.decrementActiveRequestsCount();
                }
            }).start();
        }
    }

    // 模拟请求耗时，返回耗时时间
    private static long doSomething() {
        try {
            int rt = randomValue(10, 200);
            TimeUnit.MILLISECONDS.sleep(rt);
            return rt;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    // 新线程：监控（模拟页面监控）
    private static void monitor(DataDistribution accumulator) {
        new Thread(() -> {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
            executorService.scheduleWithFixedDelay(() -> {
                System.out.println("=======时间：" + accumulator.getTimestamp() + "，统计值如下=======");
                System.out.println("统计周期：" + (accumulator.getSampleIntervalMillis() / 1000) + "s");
                System.out.println("样本数据个数：" + accumulator.getSampleSize());
                // 周期：startCollection到endCollection的时间差

                System.out.println("最大值：" + accumulator.getMaximum());
                System.out.println("最小值：" + accumulator.getMinimum());
                System.out.println("算术平均值：" + accumulator.getMean());
                System.out.println("各分位数对应值：" + Arrays.toString(accumulator.getPercentiles()));
            }, 8, 8, TimeUnit.SECONDS);
        }).start();
    }

    // 发布数据 5s发布一次数据
    private static void publishData(DataDistribution accumulator) {
        new Thread(() -> {
            new DataPublisher(accumulator, 5 * 1000).start();
        }).start();
    }

    // 新开一个线程生产数据
    private static void produceValue(DataDistribution accumulator) {
        new Thread(() -> {
            while (true) {
                accumulator.noteValue(randomValue(10, 2000));
                try {
                    TimeUnit.MILLISECONDS.sleep(randomValue(10, 200));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 本地使用随机数模拟数据收集
    private static int randomValue(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

}
