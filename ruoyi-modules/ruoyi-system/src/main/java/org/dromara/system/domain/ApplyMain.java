package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 入驻申请主对象 apply_main
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("apply_main")
public class ApplyMain extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "apply_id")
    private Long applyId;

    /**
     * 
     */
    private Long userId;

    /**
     * 1-个人 2-公会
     */
    private String applyType;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private Date submitTime;

    /**
     * 
     */
    private Date auditTime;

    /**
     * 
     */
    private String auditComment;


}
