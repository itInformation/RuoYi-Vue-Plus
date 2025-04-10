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
     * 0 待审核 1 审核通过 2 审核失败
     */
    private String status;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 备注
     */
    private String auditComment;


}
