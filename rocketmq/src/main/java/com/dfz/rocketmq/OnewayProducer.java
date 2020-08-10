package com.dfz.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName OnewayProducer
 * @Description OnewayProducer
 * @Author dfz
 * @Date 2019-07-04 10:09
 * @Version 1.0
 **/
public class OnewayProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        for (int i = 0; i < 2; i++) {
            Message message = new Message("TopicTest",
                    "TagA", ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendOneway(message);
        }
        producer.shutdown();
    }

}
