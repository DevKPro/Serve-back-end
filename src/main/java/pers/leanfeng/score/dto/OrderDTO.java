package pers.leanfeng.score.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Validated
public class OrderDTO {
    @DecimalMin(value = "0.00", message = "不在合法范围内")
    @DecimalMax(value = "99999999.99", message = "不在合法范围内")
    BigDecimal totalPrice;
    BigDecimal finalTotalPrice;
    Long couponId;
    Long serveId;   // 服务类型id
    String carNo;   // 车牌号
    String name;
    String phone;   // 手机号

}
