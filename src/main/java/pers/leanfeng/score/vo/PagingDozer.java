package pers.leanfeng.score.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
public class PagingDozer<T,VO> extends Paging {
    @SuppressWarnings("unchecked")  // 取消编译器警告
    public PagingDozer(Page<T> pageT, Class<VO> classVO){
        this.initPageParameters(pageT);
        List<T> tList = pageT.getContent();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();  // 负责深拷贝
        List<VO> voList = new ArrayList<>();
        // T 转换成 VO
        tList.forEach(t->{
            VO vo = mapper.map(t, classVO);
            voList.add(vo);
        });
        this.setItems(voList);
    }
}
