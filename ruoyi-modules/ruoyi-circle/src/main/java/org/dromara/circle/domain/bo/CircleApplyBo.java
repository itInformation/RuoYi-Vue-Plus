package org.dromara.circle.domain.bo;

import org.dromara.circle.domain.CircleApply;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;

/**
 * 圈子申请记录业务对象 circle_apply
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CircleApply.class, reverseConvertGenerate = false)
public class CircleApplyBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private String applyId;

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupId;

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
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
