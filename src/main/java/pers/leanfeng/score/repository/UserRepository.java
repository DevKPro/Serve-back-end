package pers.leanfeng.score.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.leanfeng.score.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstById(Long id);

    Optional<User> findByOpenid(String openid);
}
