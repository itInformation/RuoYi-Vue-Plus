package org.dromara.circle.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;

/**
 * 圈子主体业务对象 circle_group
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
public class CircleReviewBo{

    /**
     * 圈子ID
     */
    private String id;
    /**
     * 审核状态（0待审核 1审核通过 2审核不通过）
     */
    private Integer review;

    /**
     * 0 代表圈子审核，1 代表动态审核
     */
    private Integer type;
    /**
     * 审核失败原因，失败时填写
     */
    private String memo;
    /**
     * 前端不传此字段
     */
    private Long userId;
}
