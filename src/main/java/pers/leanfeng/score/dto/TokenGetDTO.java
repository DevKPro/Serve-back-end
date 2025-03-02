package pers.leanfeng.score.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import pers.leanfeng.score.core.enumeration.LoginType;
import pers.leanfeng.score.dto.validation.TokenPassword;

/**
 * @author leanfeng
 * @date 2025年02月28日
 */
@Getter
@Setter
public class TokenGetDTO {
    //用户的 openid 或者账号（邮箱）
    @NotBlank(message = "account不允许为空")
    private String account;
    @TokenPassword(min = 8, max = 30, message="{token.password}")
    private String password;
    private LoginType loginType;    //这里用驼峰形式，前端输入的字段名就得是下划线，login_type
}
