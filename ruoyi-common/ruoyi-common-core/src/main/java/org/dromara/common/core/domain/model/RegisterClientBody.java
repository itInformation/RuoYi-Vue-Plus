package org.dromara.common.core.domain.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * 用户注册对象
 *
 * @author Lion Li
 */
@Data
public class RegisterClientBody{

    private String tenantId;
    /**
     * 手机号
     */
    private String phonenumber;
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
