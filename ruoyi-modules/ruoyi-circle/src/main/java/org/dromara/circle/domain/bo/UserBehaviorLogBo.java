package org.dromara.circle.domain.bo;

import org.dromara.circle.domain.UserBehaviorLog;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户行为日志业务对象 user_behavior_log
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserBehaviorLog.class, reverseConvertGenerate = false)
public class UserBehaviorLogBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long logId;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 圈子ID
     */
    @NotNull(message = "圈子ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupId;

    /**
     * 关联分类ID列表
     */
    private String catIds;

    /**
     * 行为类型（1浏览 2加入 3收藏）
     */
    @NotNull(message = "行为类型（1浏览 2加入 3收藏）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long actionType;

    /**
     * 数据删除状态 0 未删除 1 删除
     */
    private Long deleted;


}
