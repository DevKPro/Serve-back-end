package pers.leanfeng.score.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.leanfeng.score.bo.UserBaseInfo;
import pers.leanfeng.score.core.LocalUser;
import pers.leanfeng.score.exception.http.NotFoundException;
import pers.leanfeng.score.model.User;
import pers.leanfeng.score.repository.UserRepository;

import java.util.Optional;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUserById(Long id){
        return userRepository.findFirstById(id);
    }

    public UserBaseInfo getUserBaseInfoById(Long id){
        User user = userRepository.findFirstById(id);
        if (user==null){
            return null;
        }
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        BeanUtils.copyProperties(user, userBaseInfo);
        return userBaseInfo;
    }

    @Transactional
    public void updateUserBaseInfo(UserBaseInfo userBaseInfo){
        Long id = LocalUser.getUser().getId();
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        BeanUtils.copyProperties(userBaseInfo, user);
        userRepository.save(user);
    }
}
