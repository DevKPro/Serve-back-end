package pers.leanfeng.score.core;


import pers.leanfeng.score.model.User;

import java.util.HashMap;
import java.util.Map;

// 一个用户就是一个线程
public class LocalUser {
    private static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<>();

    public static void set(User user, Integer scope){
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);map.put("scope",scope);
        LocalUser.threadLocal.set(map);
    }

    // 记录 User 好处是：当需要获取用户相关数据时，不用频繁查询数据库；缺点是比较占用内存空间。
    // 只记录 id 号，好处是内存占用比较小，但是当需要用户其他数据时，数据库查询比较频繁。
    public static User getUser(){
        Map<String,Object> map = LocalUser.threadLocal.get();
        return (User)map.get("user");
    }
    public static Integer getScope(){
        Map<String,Object> map = LocalUser.threadLocal.get();
        return (Integer) map.get("scope");
    }

    public static void clear(){
        LocalUser.threadLocal.remove();
    }

}
