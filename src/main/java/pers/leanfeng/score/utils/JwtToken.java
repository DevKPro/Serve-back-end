package pers.leanfeng.score.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component  //打上注解才能自动加载 @Value
public class JwtToken {
    private static String jwtKey;
    private static Integer expiredTimeIn;
    private static Integer defaultScope = 8;
    @Value("${listen-together.security.jwt-key}")
    public void setJwtKey(String jwtKey){
        JwtToken.jwtKey = jwtKey;
    }

    @Value("${listen-together.security.token-expired-in}")
    public void setExpiredTimeIn(Integer expiredTimeIn){
        JwtToken.expiredTimeIn = expiredTimeIn;
    }

    public static String makeToken(Long uid, Integer scope){
        return JwtToken.getToken(uid,scope);
    }

    public static String makeToken(Long uid){
        // 登录就给默认权限，后面可以按需要修改，或者设计权限表进行查询
        // 如会员拥有更高权限...
        return JwtToken.makeToken(uid,JwtToken.defaultScope);
    }

    public static Optional<Map<String, Claim>> getClaims(String token){
        DecodedJWT decodedJWT;
        //指定算法
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            decodedJWT = jwtVerifier.verify(token);
            return Optional.of(decodedJWT.getClaims());
        }catch (JWTVerificationException e){
            return Optional.empty();
        }
    }

    //生成根据 uid 和 scope 生成 token
    private static String getToken(Long uid, Integer scope){
        // auth0 生成 jwt 令牌
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        Map<String,Date> map = JwtToken.calculateExpiredIssues();
        String token = JWT.create()
                .withClaim("uid",uid)
                .withClaim("scope",scope)
                .withExpiresAt(map.get("expiredTime"))
                .withIssuedAt(map.get("now"))
                .sign(algorithm);
        return token;
    }

    //计算过期时间
    public static Map<String, Date> calculateExpiredIssues(){
        Map<String, Date> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.SECOND, JwtToken.expiredTimeIn);
        map.put("now",now);
        map.put("expiredTime",calendar.getTime());
        return map;
    }

    public static Boolean verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            return false;
        }
        return true;
    }


}
