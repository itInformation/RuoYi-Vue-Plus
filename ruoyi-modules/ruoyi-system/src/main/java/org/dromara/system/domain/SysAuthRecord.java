package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 【请填写功能名称】对象 sys_auth_record
 *
 * @author Lion Li
 * @date 2025-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_auth_record")
public class SysAuthRecord extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId(value = "record_id")
    private Long recordId;

    /**
     * 用户ID
     */
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
