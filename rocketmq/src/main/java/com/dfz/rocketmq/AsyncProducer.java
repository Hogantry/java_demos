package com.dfz.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AsyncProducer
 * @Description AsyncProducer
 * @Author dfz
 * @Date 2019-07-04 09:48
 * @Version 1.0
 **/
public class AsyncProducer {

    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group");
        producer.setNamesrvAddr("localhost:9876");
        producer.setSendMsgTimeout(50000);
        producer.start();
//        producer.createTopic("aaa", "TopicTest", 4);
//        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Message message = new Message("TopicTest",
                    "TagA", ("Hello RocketMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        TimeUnit.SECONDS.sleep(20);
        // 异步发送，下面最好注释掉，否则，producer已经关闭，导致异步消息发送失败。
        producer.shutdown();
    }

}
