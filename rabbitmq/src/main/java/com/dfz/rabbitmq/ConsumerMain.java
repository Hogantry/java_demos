package com.dfz.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @version V1.0
 * @author: DFZ
 * @description: rabbitmq consumer
 * @date: 2020/6/23 13:40
 * @Copyright: 2020 www.ztzqzg.com Ltd. All rights reserved.
 * 注意：本内容仅限于中泰证券（上海）资产管理有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ConsumerMain {

    public static final String QUEUE_NAME = "queue_demo";
    public static final String IP_ADDRESS = "127.0.0.1";
    public static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Address[] addresses = {new Address(IP_ADDRESS, PORT)};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
//        factory.setHost(IP_ADDRESS);
//        factory.setPort(PORT);
        // 创建连接
        Connection connection = factory.newConnection(addresses);
        Channel channel = connection.createChannel();
        // 设置客户端最多接受未被ack的消息的个数
        channel.basicQos(64);
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv message: " + new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();
    }

    private static void testMQInDocker() throws IOException, TimeoutException, InterruptedException {
        Address[] addresses = {new Address(IP_ADDRESS, PORT)};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        // virtualHost ?
        factory.setVirtualHost("my_vhost");
        // 创建连接
        Connection connection = factory.newConnection(addresses);
        Channel channel = connection.createChannel();
        // 设置客户端最多接受未被ack的消息的个数
        channel.basicQos(64);
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv message: " + new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, consumer);
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();
    }

}
