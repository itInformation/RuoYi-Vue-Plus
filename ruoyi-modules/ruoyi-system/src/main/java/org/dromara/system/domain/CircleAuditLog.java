package org.dromara.system.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 【请填写功能名称】对象 circle_audit_log
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("circle_audit_log")
public class CircleAuditLog extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "log_id")
    private Long logId;

    /**
     * 
     */
    private Long groupId;

    /**
     * 
     */
    private Long userId;

    /**
     * 操作类型
     */
    private String actionType;

    /**
     * 操作对象ID
     */
    private Long targetId;

    /**
     * 详情
     */
    private String detail;

    /**
     * 
     */
    private String ipAddress;


}
