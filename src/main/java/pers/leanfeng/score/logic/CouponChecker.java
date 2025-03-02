//package pers.leanfeng.score.logic;
//
//
//
//import pers.leanfeng.score.core.enumeration.CouponType;
//import pers.leanfeng.score.core.money.IMoneyDiscount;
//import pers.leanfeng.score.exception.http.ForbiddenException;
//import pers.leanfeng.score.exception.http.ParameterException;
//import pers.leanfeng.score.model.Coupon;
//import pers.leanfeng.score.model.Serve;
//import pers.leanfeng.score.utils.CommonUtil;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//
//
//public class CouponChecker {
//    private Coupon coupon;
////    @Autowired
//    IMoneyDiscount iMoneyDiscount;
//    public CouponChecker(Coupon coupon, IMoneyDiscount iMoneyDiscount){
//        this.coupon = coupon;
//        this.iMoneyDiscount=iMoneyDiscount;
//    }
//    //优惠券是否过期
//    public void isOk(){
//        Date now = new Date();
//        Boolean isInTimeLine = CommonUtil.isInTimeLine(now,this.coupon.getStartTime(), this.coupon.getEndTime());
//        if (!isInTimeLine) {
//            throw new ForbiddenException(40007);
//        }
//    }
//    public void finalTotalPriceIsOk(BigDecimal orderFinalTotalPrice, BigDecimal serverTotalPrice){
//        BigDecimal serverFinalTotalPrice;
//        switch (CouponType.toType(this.coupon.getType())){
//            case FULL_OFF:
//                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
//                break;
//            case FULL_MINUS:
//                serverFinalTotalPrice = this.iMoneyDiscount.discount(serverTotalPrice, this.coupon.getRate());
//                break;
//            case NO_THRESHOLD_MINUS:
//                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
//
//                break;
//            default:
//                throw new ParameterException(40009);
//        }
//        int compare = serverFinalTotalPrice.compareTo(orderFinalTotalPrice);
//        if (compare!=0){
//            throw new ForbiddenException(50008);
//        }
//        //判断是否小于等于0
//        if (serverFinalTotalPrice.compareTo(new BigDecimal("0"))<=0){
//            throw new ForbiddenException(50008);
//        }
//
//    }
//    // 核对优惠券是否能够被使用（品类）
//    public void canBeUsed(Serve orderServe, BigDecimal serverTotalPrice){
//        // sku price
//        // sku count 从 order 获取
//        // sku 所属 category id
//        BigDecimal orderCategoryPrice;  // 当前优惠券所属品类的价格总和,用于与优惠券使用门槛比较
//        if (this.coupon.getWholeStore()){   // 全场券
//            orderCategoryPrice = serverTotalPrice;
//        }
//        else{
//            List<Long> sidList = this.coupon.getServeList().stream()
//                    .map(Serve::getId)
//                    .toList();
//            orderCategoryPrice = this.getSumByServeList(orderServe, sidList);
//        }
//        this.couponCanBeUsed(orderCategoryPrice);
//    }
//
//    private void couponCanBeUsed(BigDecimal orderCategoryPrice){
//        switch (CouponType.toType(this.coupon.getType())){
//            case FULL_OFF:break;
//            case FULL_MINUS:
//                int compare = this.coupon.getFullMoney().compareTo(orderCategoryPrice);
//                if (compare>0){//未达到使用门槛
//                    throw new ParameterException(40008);
//                }
//                break;
//            case NO_THRESHOLD_MINUS:
//                break;
//            default:
//                throw new ParameterException(40009);
//        }
//    }
//
//
//    public BigDecimal getSumByServeList(Serve serve,List<Long> sidList){
//        BigDecimal sum = sidList.stream()
//                .map(sid -> this.getSumByServe(serve,sid))
//                .reduce(BigDecimal::add).orElse(new BigDecimal("0"));
//        return sum;
//    }
//
//    // 计算一个分类下所有 sku 价格总和
//    public BigDecimal getSumByServe(Serve serve, Long cid){
//        if (serve.getId().equals(cid)){
//            return serve.getPrice();
//        }
//        return new BigDecimal("0");
//    }
//}
