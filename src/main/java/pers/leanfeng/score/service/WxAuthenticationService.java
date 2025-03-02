package pers.leanfeng.score.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pers.leanfeng.score.exception.http.ParameterException;
import pers.leanfeng.score.model.User;
import pers.leanfeng.score.repository.UserRepository;
import pers.leanfeng.score.utils.CommonUtil;
import pers.leanfeng.score.utils.JwtToken;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
@Service
public class WxAuthenticationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private ObjectMapper mapper;
    @Value("${wx.code2session}")
    private String code2SessionUrl;
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appsecret}")
    private String appsecret;

    //返回 JWT令牌
    public String code2Session(String code){
        String url = MessageFormat.format(this.code2SessionUrl, this.appid, this.appsecret, code);
        RestTemplate rest = new RestTemplate();
        String sessionText = rest.getForObject(url, String.class);  //接收 openid
        Map<String,Object> session = new HashMap<>();
        try {
            session = mapper.readValue(sessionText, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return this.registerUser(session);
    }

    private String registerUser(Map<String,Object> session){
        String openid = (String) session.get("openid");
        if (openid==null){
            throw new ParameterException(20004);
        }
        Optional<User> userOptional = this.userRepository.findByOpenid(openid);
        if (userOptional.isPresent()){
            // TODO:返回 JWT令牌
            // 数字等级
            return JwtToken.makeToken(userOptional.get().getId());
        }
        // 生成随机用户名
        String uuid = UUID.randomUUID().toString();
        String name = "微信用户"+ uuid.substring(0,10);
        User user = User.builder()
                .name(name)
                .openid(openid)
                .score(0)
                .build();
        userRepository.save(user);
        Long uid = user.getId();
        // TODO:返回 JWT令牌
        return JwtToken.makeToken(uid);
    }



}
