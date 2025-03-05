package org.dromara.system.domain.bo;

import org.dromara.system.domain.CircleApply;
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
    private Long applyId;

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long groupId;

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
