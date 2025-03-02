package pers.leanfeng.score.manager.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import pers.leanfeng.score.bo.OrderMessageBO;
import pers.leanfeng.score.service.OrderCancelService;


// 过期事件监听器
public class TopicMessageListener implements MessageListener {
    @Autowired
    OrderCancelService orderCancelService;
//    @Autowired
//    CouponBackService couponBackService;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        String expiredKey = new String(body);
        String topic = new String(channel);
        OrderMessageBO orderMessageBO = new OrderMessageBO(expiredKey);
        orderCancelService.cancel(orderMessageBO);
//        couponBackService.returnBack(orderMessageBO);
    }


}
