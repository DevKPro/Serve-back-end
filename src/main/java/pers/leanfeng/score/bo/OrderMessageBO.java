package pers.leanfeng.score.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
@Setter
@Getter
public class OrderMessageBO {
    Long orderId;
    Long userId;
    Long couponId;
    String message;
    public OrderMessageBO(String message){
        this.message = message;
        this.parseId(message);
    }

    private void parseId(String message){
        String[] tmp = message.split(",");
        this.userId = Long.valueOf(tmp[0]);
        this.orderId = Long.valueOf(tmp[1]);
        this.couponId = Long.valueOf(tmp[2]);
    }

}
