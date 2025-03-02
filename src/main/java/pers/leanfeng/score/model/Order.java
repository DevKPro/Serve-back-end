package pers.leanfeng.score.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import pers.leanfeng.score.core.enumeration.OrderStatus;
import pers.leanfeng.score.utils.CommonUtil;

import java.math.BigDecimal;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")//order是 mysql 保留的关键字，需要指明
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String orderNo; //订单号
    BigDecimal totalPrice;
    String prepayId;
    BigDecimal finalTotalPrice;
    Date placedTime;     // 下单时间
    Date expiredTime;   // 过期时间

    Long uid;   // 用户id
    String carNo;   // 车牌号
    String serveName;  // 服务名称
    Long serveId;    // 服务类型
    Integer status; // 状态码
    String name;    // 下单人
    String phone;   // 手机号

    // 贫血模式、充血模式
    @JsonIgnore
    public OrderStatus getStatusEnum(){
        return OrderStatus.toType(this.status);
    }
    public Boolean needCanceled(){
        // 不是未支付的订单
        if (!this.getStatusEnum().equals(OrderStatus.UNPAID)){
            return true;
        }
        boolean isOutOfDate = CommonUtil.isOutOfDate(this.getExpiredTime());
        if (isOutOfDate)return true;
        return false;
    }
}
