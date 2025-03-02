package pers.leanfeng.score.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.leanfeng.score.core.LocalUser;
import pers.leanfeng.score.core.enumeration.OrderStatus;
import pers.leanfeng.score.dto.OrderDTO;
import pers.leanfeng.score.exception.http.NotFoundException;
import pers.leanfeng.score.exception.http.ParameterException;
//import pers.leanfeng.score.logic.CouponChecker;
import pers.leanfeng.score.logic.OrderChecker;
import pers.leanfeng.score.manager.rocketmq.ProducerSchedule;
//import pers.leanfeng.score.model.Coupon;
import pers.leanfeng.score.model.Order;
import pers.leanfeng.score.model.Serve;
import pers.leanfeng.score.repository.OrderRepository;
import pers.leanfeng.score.repository.ServeRepository;
import pers.leanfeng.score.utils.CommonUtil;
import pers.leanfeng.score.utils.OrderUtil;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ServeRepository serveRepository;

    @Value("${listen-together.order.pay-time-limit}")
    Integer payTimeLimit;
    @Value("${rocketmq.topic}")
    String topic;

    @Autowired
    ProducerSchedule producerSchedule;


    // 查询订单列表
    public Page<Order> getList(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending()); // 根据创建时间倒序排序
        Long uid = LocalUser.getUser().getId();
        return this.orderRepository.findByUid(uid, pageable);
    }


    public OrderChecker isOk(Long uid, OrderDTO orderDTO){
        if (orderDTO.getTotalPrice().compareTo(new BigDecimal("0"))<=0){
            throw new ParameterException(50011);
        }
        //校验每个单品信息
        Long serveId = orderDTO.getServeId();
        Serve serve = serveRepository.findById(serveId)
                .orElseThrow(()->new NotFoundException(50002));

        // OrderChecker   CouponChecker
//        Long coponId = orderDTO.getCouponId();
//        CouponChecker couponChecker = null;
//        if (coponId!=null){     //使用了优惠券
//            // 确保这张优惠券存在
//            Coupon coupon = couponRepository.findById(coponId)
//                    .orElseThrow(()->new NotFoundException(40003));
//            // 确保用户拥有这张券
//            UserCoupon userCoupon = userCouponRepository.findFirstByUserIdAndCouponIdAndStatusAndOrderIdIsNull(uid,coponId, CouponStatus.AVAILABLE.getValue())
//                    .orElseThrow(()->new NotFoundException(50006));
//            // 确保优惠券有效
//            couponChecker = new CouponChecker(coupon,iMoneyDiscount);
//        }
//        OrderChecker orderChecker = new OrderChecker(orderDTO,serve,couponChecker);
        OrderChecker orderChecker = new OrderChecker(orderDTO,serve);
        orderChecker.isOk();
        return orderChecker;
    }


    @Transactional      // 事务:因为 1.对很多张表操作；2.避免宕机等问题；
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker){
        // 进入此方法时，说明 orderDTO 已经完成校验了，是可信的
        String orderNo = OrderUtil.makeOrderNo();
        Calendar now = Calendar.getInstance();

        // 下单时间
        Date placedTime = now.getTime();
        // 计算过期时间
        Date expiredTime = CommonUtil.addSomeSeconds(now, this.payTimeLimit).getTime();

        // 构建订单
        Order order = Order.builder()
                .orderNo(orderNo)
                .totalPrice(orderDTO.getTotalPrice())
                .finalTotalPrice(orderDTO.getFinalTotalPrice())
                .carNo(orderDTO.getCarNo())
                .serveId(orderDTO.getServeId())
                .serveName(orderChecker.getServerServe().getName())
                .uid(uid)
                .status(OrderStatus.UNPAID.value())
                .placedTime(placedTime)
                .expiredTime(expiredTime)
                .build();

        this.orderRepository.save(order);

        // 如果使用了优惠券，则需要核销优惠券
        Long couponId = -1L;
//        if (orderDTO.getCouponId()!=null) {
//            this.writeOffCoupon(orderDTO.getCouponId(), order.getId(), uid);
//            couponId = orderDTO.getCouponId();
//        }
        // 加入到延迟消息队列（解决超时未付款库存、优惠券归还问题）
//        this.sendToRedis(order.getId(), uid, couponId);
        this.sendToRocketMQ(order.getId(), uid, couponId);
        return order.getId();
    }


    private void sendToRocketMQ(Long oid, Long uid, Long couponId){
        String key = uid.toString()+","+oid.toString()+","+couponId.toString();
        try {
            producerSchedule.send(topic, key);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
