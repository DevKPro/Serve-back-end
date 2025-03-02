package pers.leanfeng.score.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.leanfeng.score.model.Serve;

public interface ServeRepository extends JpaRepository<Serve, Long> {
    public String findNameById(Long id);

}
