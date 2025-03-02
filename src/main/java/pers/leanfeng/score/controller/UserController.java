package pers.leanfeng.score.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.leanfeng.score.bo.UserBaseInfo;
import pers.leanfeng.score.core.LocalUser;
import pers.leanfeng.score.core.interceptor.ScopeLevel;
import pers.leanfeng.score.service.UserService;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("baseInfo")
    @ResponseBody
    @ScopeLevel()
    public UserBaseInfo getUserInfo(){
        Long id = LocalUser.getUser().getId();
        return userService.getUserBaseInfoById(id);
    }

    @PostMapping("wx_info")
    @ScopeLevel()
    public String updateUserInfo(@RequestBody UserBaseInfo userBaseInfo){
        userService.updateUserBaseInfo(userBaseInfo);
        return "ok";
    }

//    @PostMapping("wx_info")
//    public String getUserInfo(Long id){
//        return userService.getUserById(id).toString();
//    }
}
