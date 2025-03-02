package pers.leanfeng.score.utils;

import pers.leanfeng.score.bo.PageCounter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
// 将 start和 count 做转换,转换成所需的 pageNum 格式
public class CommonUtil {
    public static PageCounter convertToPageParameter(Integer start, Integer count){
        int pageNum = start/count;
        PageCounter pageCounter = PageCounter.builder()
                .page(pageNum)
                .count(count).build();
        return pageCounter;
    }

    public static Calendar addSomeSeconds(Calendar calendar, int seconds){
        calendar.add(Calendar.SECOND, seconds);
        return calendar;
    }

    public static Boolean isInTimeLine(Date date, Date start, Date end){
        Long time = date.getTime();
        Long startTime = start.getTime();
        Long endTime = end.getTime();
        return (time>startTime && time<endTime);
    }

    public static Boolean isOutOfDate(Date expiredTime){
        Date now = new Date();
        return now.after(expiredTime);
    }

    public static String toPlain(BigDecimal p){
        return p.stripTrailingZeros().toPlainString();
    }

    public static String yuanToFenPlainString(BigDecimal num){
        BigDecimal p = num.multiply(new BigDecimal("100"));
        return CommonUtil.toPlain(p);
    }

}
