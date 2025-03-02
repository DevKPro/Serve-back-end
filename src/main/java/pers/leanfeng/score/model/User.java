package pers.leanfeng.score.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Where;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
@Table(name = "user", schema = "score")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String openid;
    String name;
    String phone;
    String avatar;
    String address;
    String carNo;
    Integer score;

}
