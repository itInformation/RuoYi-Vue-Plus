package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysRealnameAuth;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 【请填写功能名称】业务对象 sys_realname_auth
 *
 * @author Lion Li
 * @date 2025-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysRealnameAuth.class, reverseConvertGenerate = false)
public class SysRealnameAuthBo extends BaseEntity {

    /**
     * 认证ID
     */
    @NotNull(message = "认证ID不能为空", groups = { EditGroup.class })
    private String authId;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String realName;

    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不能为空", groups = { AddGroup.class, EditGroup.class })
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
