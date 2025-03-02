package pers.leanfeng.score.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.leanfeng.score.bo.PageCounter;
import pers.leanfeng.score.core.LocalUser;
import pers.leanfeng.score.core.interceptor.ScopeLevel;
import pers.leanfeng.score.dto.OrderDTO;
import pers.leanfeng.score.logic.OrderChecker;
import pers.leanfeng.score.model.Order;
import pers.leanfeng.score.service.OrderService;
import pers.leanfeng.score.utils.CommonUtil;
import pers.leanfeng.score.vo.OrderIdVO;
import pers.leanfeng.score.vo.OrderSimplifyVO;
import pers.leanfeng.score.vo.PagingDozer;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
@RestController
@Validated
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("orderList")
    @ScopeLevel()
    public PagingDozer getOrderList(@RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "10") Integer count){
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = orderService.getList(page.getPage(), page.getCount());
        PagingDozer<Order, OrderSimplifyVO> pagingDozer = new PagingDozer<>(orderPage, OrderSimplifyVO.class);
        return pagingDozer;
    }

    // 用户提交订单
    @ScopeLevel()
    @PostMapping("")
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO){
        Long uid = LocalUser.getUser().getId();
        //信息校验：检验前后端商品信息是否一致
        // OrderChecker   CouponChecker
        OrderChecker orderChecker = this.orderService.isOk(uid,orderDTO);
        //下单
        Long oid = this.orderService.placeOrder(uid,orderDTO,orderChecker);
        return new OrderIdVO(oid);
    }


    @PostMapping("test")
    public OrderIdVO testPlaceOrder(@RequestBody OrderDTO orderDTO){
        Long uid = 1L;
        //信息校验：检验前后端商品信息是否一致
        // OrderChecker   CouponChecker
        OrderChecker orderChecker = this.orderService.isOk(uid,orderDTO);
        //下单
        Long oid = this.orderService.placeOrder(uid,orderDTO,orderChecker);
        return new OrderIdVO(oid);
    }

}
