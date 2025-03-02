package pers.leanfeng.score.core.interceptor;

import com.auth0.jwt.interfaces.Claim;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import pers.leanfeng.score.core.LocalUser;
import pers.leanfeng.score.exception.http.ForbiddenException;
import pers.leanfeng.score.exception.http.UnAuthenticatedException;
import pers.leanfeng.score.model.User;
import pers.leanfeng.score.service.UserService;
import pers.leanfeng.score.utils.JwtToken;


import java.util.Map;
import java.util.Optional;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 全局权限拦截器，使用 interceptor，需实现 HandlerInterceptor 接口
//@Component
//PermissionInterceptor不会被注入到IOC容器
// 在 InterceptorConfiguration 将 PermissionInterceptor注入到容器
public class PermissionInterceptor implements HandlerInterceptor{
    // 因为注入到容器，所以才能使用@Autowired
    @Autowired
    UserService userService;

    // 获取 API方法上的 ScopeLevel 注解，以获得 level 值
    private Optional<ScopeLevel> getScopeLevel(Object handler){
        // 对方法拦截
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            //解决没有带@ScopeLevel接口接收到为 null 的情况
            if (scopeLevel==null){
                return Optional.empty();
            }
            return Optional.of(scopeLevel);
        }
        return Optional.empty();
    }

    @Override
    // handler 是指要访问的 API方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<ScopeLevel> scopeLevel = this.getScopeLevel(handler);
        // 没有注解，即不需要权限
        if (!scopeLevel.isPresent()){
            return true;
        }
        String bearerToken = request.getHeader("Authorization");
        //空，无效 token
        if (StringUtils.isEmpty(bearerToken)){
            throw new UnAuthenticatedException(10004);
        }
        //判断是否符合 JWT 格式
        //格式：Bearer {token}
        if (!bearerToken.startsWith("Bearer")){
            throw new UnAuthenticatedException(10004);
        }
        String[] tokens = bearerToken.split(" ");
        //判空操作，避免数组越界
        if (!(tokens.length==2)){
            throw new UnAuthenticatedException(10004);
        }
        //获取纯 token
//        String token = bearerToken.split(" ")[1];
        String token = tokens[1];
        Optional<Map<String, Claim>> optionalMap = JwtToken.getClaims(token);
        Map<String,Claim> map = optionalMap.orElseThrow(()->new UnAuthenticatedException(10004));
        // 是否有权访问
        boolean valid = hasPermission(scopeLevel.get(), map);
        // 需要保证线程安全
        if (valid){
            this.setToThreadLocal(map);
        }
        return valid;
    }

    private void setToThreadLocal(Map<String,Claim> map){
        Long uid = map.get("uid").asLong();
        Integer scope = map.get("scope").asInt();
        User user = userService.getUserById(uid);
        LocalUser.set(user, scope);
    }

    boolean hasPermission(ScopeLevel scopeLevel, Map<String,Claim> map){
        Integer level = scopeLevel.value();
        Integer scope = map.get("scope").asInt();
        //用户权限不足
        if (level>scope) {
            throw new ForbiddenException(10005);
//            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清除保存的线程信息
        LocalUser.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


}
