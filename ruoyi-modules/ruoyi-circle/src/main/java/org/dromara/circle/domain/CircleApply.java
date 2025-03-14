package org.dromara.circle.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 圈子申请记录对象 circle_apply
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("circle_apply")
public class CircleApply extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "apply_id", type = IdType.ASSIGN_ID)
    private String applyId;

    /**
     *
     */
    private String groupId;

    /**
     *
     */
    private Long userId;

    /**
     * 申请理由
     */
    private String applyReason;

    /**
     * 状态（0待处理 1通过 2拒绝）
     */
    private Long applyStatus;

    /**
     * 审核人
     */
    private Long auditUser;

    /**
     *
     */
    private Date auditTime;


}
