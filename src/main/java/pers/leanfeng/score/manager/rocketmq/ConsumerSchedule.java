package pers.leanfeng.score.manager.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pers.leanfeng.score.bo.OrderMessageBO;
import pers.leanfeng.score.service.OrderCancelService;


@Component
public class ConsumerSchedule implements CommandLineRunner {
    @Value("${rocketmq.consumer.consumer-group}")
    String consumerGroup;

    @Value("${rocketmq.namesrv-addr}")
    String namesrvAddr;
    @Value("${rocketmq.topic}")
    String topic;

    @Autowired
    OrderCancelService orderCancelService;
//    @Autowired
//    CouponBackService couponBackService;

    // 消息监听
    public void messageListener() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(this.namesrvAddr);
        // 监听指定 topic 的消息
        consumer.subscribe(topic, "*");
        // 一次消费几条消息
        consumer.setConsumeMessageBatchMaxSize(1);
        // 接收到到期消息后的操作
        // 匿名类
        consumer.registerMessageListener((MessageListenerConcurrently) (messages, context)->{
            for (Message message:messages){
                OrderMessageBO orderMessageBO = new OrderMessageBO(new String(message.getBody()));
                orderCancelService.cancel(orderMessageBO);
//                couponBackService.returnBack(orderMessageBO);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //启动消费者
        consumer.start();
    }

    @Override
    public void run(String... args) throws Exception {
        this.messageListener();
    }
}
