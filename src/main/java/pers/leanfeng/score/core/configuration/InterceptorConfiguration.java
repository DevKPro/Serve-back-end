package pers.leanfeng.score.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.leanfeng.score.core.interceptor.PermissionInterceptor;

//全局拦截器
//@Component
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    //利用@Bean 将PermissionInterceptor注入到容器
    @Bean
    public HandlerInterceptor getPermissionInterceptor(){
        return new PermissionInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //将权限拦截器加入到拦截器中
        registry.addInterceptor(this.getPermissionInterceptor());
        // 下面这个方法是直接 new 一个PermissionInterceptor，跟PermissionInterceptor打没打注解没关系
        // 因此PermissionInterceptor不会被加入到容器！！！
//        registry.addInterceptor(new PermissionInterceptor());
//        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
