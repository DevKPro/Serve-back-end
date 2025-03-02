package pers.leanfeng.score.core.enumeration;

//枚举是一种受限的类
public enum LoginType {
    USER_WX(0,"微信登录"),USER_Email(1,"邮箱登录");

    private Integer value;
    LoginType(Integer value,String description){
        this.value=value;
    }
}
