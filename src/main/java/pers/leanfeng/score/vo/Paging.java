package pers.leanfeng.score.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
// 返回给视图层的对象
@Getter
@Setter
@NoArgsConstructor
public class Paging<T> {
    Long total;
    Integer page;
    Integer count;
    Integer totalPage;
    List<T> items;

    public Paging(Page<T> pageT){
        this.initPageParameters(pageT);
        this.items = pageT.getContent();
    }
    // 只保留前端关注的参数
    void initPageParameters(Page<T> pageT){
        this.total = pageT.getTotalElements();
        this.count = pageT.getSize();
        this.page = pageT.getNumber();
        this.totalPage = pageT.getTotalPages();
    }
}