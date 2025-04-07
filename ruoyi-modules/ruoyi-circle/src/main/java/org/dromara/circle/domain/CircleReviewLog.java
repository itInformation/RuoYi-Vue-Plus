package org.dromara.circle.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 圈子审核记录对象 circle_review_log
 *
 * @author Lion Li
 * @date 2025-04-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("circle_review_log")
public class CircleReviewLog extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 圈子Id
     */
    private Long circleId;

    /**
     * 圈子名称
     */
    private String circleName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 审核结果 0待审核，1 审核成功 2 审核失败
     */
    private Long review;

    /**
     * 审核失败原因
     */
    private String memo;
    /**
     * 审核类型 0 圈子 1 动态
     */
    private String type;


}
