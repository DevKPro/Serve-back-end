package pers.leanfeng.score.core.money;

import java.math.BigDecimal;

public interface IMoneyDiscount {
    // 输入原价和折扣率
    public BigDecimal discount(BigDecimal original,BigDecimal discount);
}
