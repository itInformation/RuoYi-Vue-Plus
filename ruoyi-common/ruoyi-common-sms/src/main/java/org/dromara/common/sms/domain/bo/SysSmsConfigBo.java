package org.dromara.common.sms.domain.bo;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.dromara.common.sms.domain.SysSmsConfig;

/**
 * 短信配置业务对象 sys_sms_config
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysSmsConfig.class, reverseConvertGenerate = false)
public class SysSmsConfigBo extends BaseEntity {

    /**
     * 配置主键
     */
    @NotNull(message = "配置主键不能为空", groups = { EditGroup.class })
    private Long configId;

    /**
     * 厂商类型（ALIBABA/TENCENT/...）
     */
    @NotBlank(message = "厂商类型（ALIBABA/TENCENT/...）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String supplier;

    /**
     * 访问密钥
     */
    @NotBlank(message = "访问密钥不能为空", groups = { AddGroup.class, EditGroup.class })
    private String accessKey;

    /**
     * 密钥
     */
    @NotBlank(message = "密钥不能为空", groups = { AddGroup.class, EditGroup.class })
    private String secretKey;

    /**
     * 短信签名
     */
    @NotBlank(message = "短信签名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String signature;

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 状态（0正常 1停用）
     */
    private String status;
    /**
     * 租户编号
     */
    private String tenantId;

}
