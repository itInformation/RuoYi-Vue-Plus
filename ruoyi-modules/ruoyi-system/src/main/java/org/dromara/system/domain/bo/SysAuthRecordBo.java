package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysAuthRecord;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 【请填写功能名称】业务对象 sys_auth_record
 *
 * @author Lion Li
 * @date 2025-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysAuthRecord.class, reverseConvertGenerate = false)
public class SysAuthRecordBo extends BaseEntity {

    /**
     * 记录ID
     */
    @NotNull(message = "记录ID不能为空", groups = { EditGroup.class })
    private Long recordId;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 认证类型（FACE人脸）
     */
    private String authType;

    /**
     * 认证结果（0成功 1失败）
     */
    private String result;

    /**
     * 比对分数
     */
    private Long score;

    /**
     * 失败原因
     */
    private String reason;

    /**
     * 认证时间
     */
    private Date authTime;


}
