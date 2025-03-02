package pers.leanfeng.score.logic;

import lombok.Getter;
import pers.leanfeng.score.dto.OrderDTO;
import pers.leanfeng.score.exception.http.ParameterException;
import pers.leanfeng.score.model.Serve;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


//@Service
//@Scope("prototype")         // 开启多例模式
public class OrderChecker {
    // 内部需要调用 CouponChecker
    // 外观模式  facade
    // OrderService -> OrderChecker -> CouponChecker

    // 校验  order
    // 校验 OrderDTO，前端传来的不可信

    OrderDTO orderDTO;

    @Getter
    Serve serverServe;
//    CouponChecker couponChecker;



    // 在bean 中，只有交给容器托管的对象才能作为参数
    // 或者在 Configuration 中实例化
    public OrderChecker(OrderDTO orderDTO, Serve serverServe){
        this.orderDTO = orderDTO;
        this.serverServe = serverServe;
    }
//    public OrderChecker(OrderDTO orderDTO, Serve serverServe, CouponChecker couponChecker){
//        this.orderDTO = orderDTO;
//        this.serverServe = serverServe;
//        this.couponChecker = couponChecker;
//    }

    public void isOk(){
        // orderTotalPrice   serverTotalPrice 校验前端和后端价格是否一致
        // sku是否已经下架
        // sku实际是否售罄
        // 数量是否超过库存
        // 数量是否超过规定上限
        // 优惠券 CouponChecker

        // 比较前端传来的 sku 数量与服务端查询到的 sku 数量比较

        BigDecimal serverTotalPrice = serverServe.getPrice();

        // 前后端总价是否一致
        this.totalPriceIsOk(orderDTO.getTotalPrice(), serverTotalPrice);
//        // 验证优惠券
//        if (this.couponChecker!=null){
//            this.couponChecker.isOk();
//            this.couponChecker.canBeUsed(orderDTO, serverTotalPrice);
//            this.couponChecker.finalTotalPriceIsOk(orderDTO.getFinalTotalPrice(), serverTotalPrice);
//        }else{
//            if (serverTotalPrice.compareTo(orderDTO.getFinalTotalPrice())!=0){
//                throw new ParameterException(50005);
//            }
//        }

        if (serverTotalPrice.compareTo(orderDTO.getFinalTotalPrice())!=0){
            throw new ParameterException(50005);
        }
    }


    private void totalPriceIsOk(BigDecimal orderTotalPrice,BigDecimal serverTotalPrice){
        if (orderTotalPrice.compareTo(serverTotalPrice)!=0){
            throw new ParameterException(50005);
        }
    }


}
