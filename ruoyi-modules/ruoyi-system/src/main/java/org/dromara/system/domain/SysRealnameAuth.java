package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 【请填写功能名称】对象 sys_realname_auth
 *
 * @author Lion Li
 * @date 2025-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_realname_auth")
public class SysRealnameAuth extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 认证ID
     */
    @TableId(value = "auth_id")
    private String authId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 身份证正面
     */
    private String idcardFront;

    /**
     * 身份证反面
     */
    private String idcardBack;

    /**
     * 人脸特征数据
     */
    private String faceData;

    /**
     * 认证状态（0待认证 1通过 2失败）
     */
    private String status;


}
