package pers.leanfeng.score.bo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
// bo: business object 业务对象
@Getter
@Setter
@Builder
// 分页对象
public class PageCounter {
    Integer page;
    Integer count;
}

