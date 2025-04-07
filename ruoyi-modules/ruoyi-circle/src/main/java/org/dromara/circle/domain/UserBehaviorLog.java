package org.dromara.circle.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户行为日志对象 user_behavior_log
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_behavior_log")
public class UserBehaviorLog extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "log_id")
    private Long logId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 圈子ID
     */
    private String groupId;

    /**
     * 关联分类ID列表
     */
    private String catIds;

    /**
     * 行为类型（1浏览 2加入 3收藏）
     */
    private Long actionType;

    /**
     * 数据删除状态 0 未删除 1 删除
     */
    private Long deleted;


}
