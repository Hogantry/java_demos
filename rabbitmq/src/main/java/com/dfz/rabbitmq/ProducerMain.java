package com.dfz.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @version V1.0
 * @author: DFZ
 * @description: rabbitmq producer
 * @date: 2020/6/23 13:26
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ProducerMain {

    public static final String EXCHANGE_NAME = "exchange_demo";
    public static final String ROUTING_KEY = "routing_key_demo";
    public static final String QUEUE_NAME = "queue_demo";
    public static final String IP_ADDRESS = "127.0.0.1";
    public static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 创建一个type="direct"、持久化、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, null);
        // 创建一个持久化、非排他、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 将交换器与队列通过路由键绑定 第三个参数准确来说应该是绑定key而非路由key，但一般情况下是复用路由key的
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        String message = "Hello World!";
        // 当mandatory为true，如果交换器无法根据自身的类型和路由键找到一个符合条件的队列，则会把消息返回给生产者，生产者通过channel.addReturnListener监听器来获得被返回的消息；
        //            默认为false，出现上述情形，则消息直接丢弃；
        // 当immediate为true，如果路由到的所有队列上都没有任何消费者时，那么这条消息不会存入队列中，会返回给生产者；
        //           RabbitMQ 3.0去掉该参数的支持，默认为false
//        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, true, true, MessageProperties.PERSISTENT_TEXT_PLAIN,
//                message.getBytes());
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        // 关闭资源
        channel.close();
        connection.close();
    }

    private static void testMQInDocker() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("my_vhost");
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 创建一个type="direct"、持久化、非自动删除的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, null);
        // 创建一个持久化、非排他、非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 将交换器与队列通过路由键绑定 第三个参数准确来说应该是绑定key而非路由key，但一般情况下是复用路由key的
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        String message = "Hello World!";
        // 当mandatory为true，如果交换器无法根据自身的类型和路由键找到一个符合条件的队列，则会把消息返回给生产者，生产者通过channel.addReturnListener监听器来获得被返回的消息；
        //            默认为false，出现上述情形，则消息直接丢弃；
        // 当immediate为true，如果路由到的所有队列上都没有任何消费者时，那么这条消息不会存入队列中，会返回给生产者；
        //           RabbitMQ 3.0去掉该参数的支持，默认为false
//        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, true, true, MessageProperties.PERSISTENT_TEXT_PLAIN,
//                message.getBytes());
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        // 关闭资源
        channel.close();
        connection.close();
    }

}
