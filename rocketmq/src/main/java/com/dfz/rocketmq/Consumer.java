package com.dfz.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * @ClassName Consumer
 * @Description Consumer
 * @Author dfz
 * @Date 2019-07-04 09:55
 * @Version 1.0
 **/
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group");
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe("TopicTest","*");
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            System.out.printf("%s Receive New Messages: %s list size：%s %n", Thread.currentThread().getName(), list, list.size());
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        System.out.println("Consumer Started.");
    }

}
