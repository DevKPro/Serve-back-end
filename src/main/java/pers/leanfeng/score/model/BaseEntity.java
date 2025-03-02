package pers.leanfeng.score.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @JsonIgnore
    @CreationTimestamp
    Date createTime;
    @JsonIgnore
//    @Column(insertable = false, updatable = false)
//    @LastModifiedDate
    @UpdateTimestamp
    Date updateTime;
    @JsonIgnore
    Date deleteTime;
}
