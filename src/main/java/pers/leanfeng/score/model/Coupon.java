//package pers.leanfeng.score.model;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToMany;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.Where;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@Where(clause = "delete_time is null")
//public class Coupon extends BaseEntity{
//    @Id
//    Long id;
//    Long activityId;
//    String title;
//    Date startTime;
//    Date endTime;
//    String description;
//    BigDecimal fullMoney;//满多少
//    BigDecimal minus;//减多少
//    BigDecimal rate;//折扣率
//    String remark;//标识、描述
//    Boolean wholeStore;//是否全场券
//    Integer type;//满减券、折扣券、无门槛券、满金额折扣券
//
//    // 导航属性
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "couponList")
//    List<Serve> serveList;
//
//}
