package pers.leanfeng.score.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
@Entity
@Getter
public class Serve extends BaseEntity{
    @Id
    Long id;
    String name;
    BigDecimal price;
    Integer duration;
}
