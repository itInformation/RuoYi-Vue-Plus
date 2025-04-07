package org.dromara.circle.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.EditGroup;

/**
 * 圈子内容置顶业务对象 circle_content
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data

public class CircleContentReviewBo {
    /**
     * 内容ID
     */
    @NotNull(message = "内容ID不能为空", groups = { EditGroup.class })
    private String contentId;
    /**
     * 审核状态（0待审核 1审核通过 2审核不通过）
     */
    private Integer review;





}
