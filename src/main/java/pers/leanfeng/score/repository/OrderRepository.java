package pers.leanfeng.score.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pers.leanfeng.score.model.Order;

import java.util.Optional;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUid(Long uid, Pageable pageable);

    Optional<Order> findFirstByUidAndId(Long uid, Long id);

    Optional<Order> findFirstByOrderNo(String oid);

    @Modifying
    @Query("update Order o set o.status=:status where o.orderNo=:orderNo")
    int updateStatusByOrderNo(String orderNo, Integer status);

    @Modifying
    @Query("update Order o set o.status=:cancelStatus where o.status=1 and o.id=:oid")
    int cancelOrder(Long oid, Integer cancelStatus);
}
