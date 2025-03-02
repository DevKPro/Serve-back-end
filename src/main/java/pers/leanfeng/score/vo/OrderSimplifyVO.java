package pers.leanfeng.score.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */

@Getter
@Setter
public class OrderSimplifyVO {
    String serveName;  // 服务名称
    Long serveId;    // 服务类型
    Integer status;     // 服务状态
}
