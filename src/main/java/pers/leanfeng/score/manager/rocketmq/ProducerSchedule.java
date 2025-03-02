package pers.leanfeng.score.manager.rocketmq;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProducerSchedule {
    @Value("${rocketmq.producer.producer-group}")
    String producerGroup;
    @Value("${rocketmq.namesrv-addr}")
    String namesrvAddr;
    private DefaultMQProducer producer;

    public ProducerSchedule() {
    }

    @PostConstruct
    public void defaultMQProducer(){
        if (producer==null){
            this.producer = new DefaultMQProducer(this.producerGroup);
            this.producer.setNamesrvAddr(this.namesrvAddr);
        }
        try {
            producer.start();
            System.out.println("-----------------------producer start---------------------- ");
        } catch (MQClientException e){
            e.printStackTrace();
        }
    }

    public String send(String topic, String messageText) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        Message message = new Message(topic, messageText.getBytes());
        // messageDelayLevel=ls 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h
        message.setDelayTimeLevel(4);
        SendResult sendResult = producer.send(message);
        return sendResult.getMsgId();
    }

    public String sendWaitingQueue(String topic, String messageText) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        Message message = new Message(topic, messageText.getBytes());
        // messageDelayLevel=ls 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h
        message.setDelayTimeLevel(4);
        SendResult sendResult = producer.send(message);
        return sendResult.getMsgId();
    }
}
