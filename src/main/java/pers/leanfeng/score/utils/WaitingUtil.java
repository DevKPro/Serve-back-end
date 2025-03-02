package pers.leanfeng.score.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;


// 排队工具类
@Component
public class WaitingUtil {
    private static Date endTime;
    private static Integer count=0;

    public static Integer getCount(){
        return count;
    }
    public static void setCount(Integer count){
        WaitingUtil.count=count;
    }
    public static void addCount(){
        WaitingUtil.count++;
    }
    public static void subCount(){
        WaitingUtil.count--;
    }


    public static Date getEndTime(){
        return endTime;
    }

    public static void setEndTime(Date time){
        endTime=time;
    }
}
