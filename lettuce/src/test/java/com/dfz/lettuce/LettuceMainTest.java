package com.dfz.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

class LettuceMainTest {

    private static RedisClient redisClient;
    private static StatefulRedisConnection<String, String> connection;

    @BeforeAll
    public static void beforeAll() {
        RedisURI redisURI = RedisURI.builder()                                  // <1> 创建单机连接的连接信息
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        // <2> 创建客户端
        redisClient = RedisClient.create(redisURI);
        connection = redisClient.connect();
    }

    @AfterAll
    public static void afterAll() {
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void testSetGet() {
        RedisURI redisURI = RedisURI.builder()                                  // <1> 创建单机连接的连接信息
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        RedisClient redisClient = RedisClient.create(redisURI);                 // <2> 创建客户端
        StatefulRedisConnection<String, String> connection =
                redisClient.connect();                                          // <3> 创建线程安全的连接
        RedisCommands<String, String> redisCommands = connection.sync();        // <4> 创建同步命令
        SetArgs setArgs = SetArgs.Builder.nx().ex(5);
        String result = redisCommands.set("name", "throwable", setArgs);   // setArgs包含nx()方法，如果redis中已存在name，则返回null
        System.out.println("1:" + result);
        Assertions.assertEquals(result, "OK");
        result = redisCommands.get("name");
        System.out.println("2:" + result);
        Assertions.assertEquals(result, "throwable");

        connection.close();   // <5> 关闭连接
        redisClient.shutdown();   // <6> 关闭客户端
    }

}