package pers.leanfeng.score.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.leanfeng.score.dto.TokenDTO;
import pers.leanfeng.score.dto.TokenGetDTO;
import pers.leanfeng.score.exception.http.NotFoundException;
import pers.leanfeng.score.service.WxAuthenticationService;
import pers.leanfeng.score.utils.JwtToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */

@RestController()
@RequestMapping("token")
public class TokenController {
    @Autowired
    WxAuthenticationService wxAuthenticationService;


    @PostMapping("")
    public Map<String,String> getToken(@RequestBody @Validated TokenGetDTO userData){
        Map<String,String> map = new HashMap<>();
        String token = null;
        switch (userData.getLoginType()){
            case USER_WX:
                token = wxAuthenticationService.code2Session(userData.getAccount());
                break;
//            case USER_Email:
//                token = authenticationService.getTokenByEmail(userData);
//                break;
            default:
                throw new NotFoundException(10003);
        }
        map.put("token",token);
        return map;
    }

    //验证接口
    @PostMapping("verify")
    public Map<String,Boolean> verify(@RequestBody TokenDTO tokenDTO){
        Map<String,Boolean> map = new HashMap<>();
        Boolean valid = JwtToken.verifyToken(tokenDTO.getToken());
        map.put("is_valid",valid);
        return map;
    }




}
