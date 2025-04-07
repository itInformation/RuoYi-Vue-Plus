package org.dromara.circle.domain.bo;

import org.dromara.circle.domain.CircleReviewLog;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 圈子审核记录业务对象 circle_review_log
 *
 * @author Lion Li
 * @date 2025-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CircleReviewLog.class, reverseConvertGenerate = false)
public class CircleReviewLogBo extends BaseEntity {

    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 圈子Id
     */
    private String circleId;

    /**
     * 圈子名称
     */
    @NotBlank(message = "圈子名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String circleName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 审核结果 0待审核，1 审核成功 2 审核失败
     */
    private Integer review;

    /**
     * 审核失败原因
     */
    private String memo;

    /**
     * 审核类型 0 圈子 1 动态
     */
    private Integer type;
}
