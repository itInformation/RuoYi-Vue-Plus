package org.dromara.common.core.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户注册对象
 *
 * @author Lion Li
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterBody extends LoginBody {

    /**
     * 用户名
     */
    @NotBlank(message = "{user.username.not.blank}")
    @Length(min = 2, max = 20, message = "{user.username.length.valid}")
    private String username;

    private String password;

    private String userType;
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户性别 0男 1女
     */
    private String sex;

    /**
     * 用户头像 传 ossId 框架会自动转话成url
     */
    private Long avatar;

    /**
     * 出生年月 日期格式
     */
    private LocalDate birthday;

}
