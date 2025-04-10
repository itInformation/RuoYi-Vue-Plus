package org.dromara.system.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.system.domain.ApplyMain;

import java.util.Date;

/**
 * 入驻申请主业务对象 apply_main
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Data
public class ApplyMainReviewBo {

    /**
     * 主键
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long applyId;

    /**
     * 审核状态 0 待审核 1 审核通过 2 审核失败
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;
    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     *  备注
     */
    private String auditComment;


}
