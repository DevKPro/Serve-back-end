package pers.leanfeng.score.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.leanfeng.score.bo.OrderMessageBO;
import pers.leanfeng.score.core.enumeration.OrderStatus;
import pers.leanfeng.score.exception.http.ServerErrorException;
import pers.leanfeng.score.model.Order;
import pers.leanfeng.score.repository.OrderRepository;

import java.util.Optional;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
@Service
public class OrderCancelService {
    @Autowired
    OrderRepository orderRepository;

    @Transactional
    public void cancel(OrderMessageBO messageBO){
        if (messageBO.getOrderId()<=0){
            throw new ServerErrorException(9999);
        }
        this.cancel(messageBO.getUserId(),messageBO.getOrderId());
    }

    private void cancel(Long uid, Long oid){
        Optional<Order> orderOptional = orderRepository.findFirstByUidAndId(uid,oid);
        Order order = orderOptional.orElseThrow(()->{
            throw new ServerErrorException(9999);
        });
        int res = orderRepository.cancelOrder(oid, OrderStatus.CANCELED.value());
        if (res!=1){    // 没更新成功
//            throw new ServerErrorException(9999);
            return;
        }

    }
}
