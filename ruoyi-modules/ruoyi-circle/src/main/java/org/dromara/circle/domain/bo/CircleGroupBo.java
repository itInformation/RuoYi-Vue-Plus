package org.dromara.circle.domain.bo;

import org.dromara.circle.domain.CircleGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 圈子主体业务对象 circle_group
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CircleGroup.class, reverseConvertGenerate = false)
public class CircleGroupBo extends BaseEntity {

    /**
     * 圈子ID
     */
    @NotNull(message = "圈子ID不能为空", groups = { EditGroup.class })
    private String groupId;

    /**
     * 圈子名称
     */
    @NotBlank(message = "圈子名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupName;

    /**
     * 圈子描述
     */
    private String description;

    /**
     * 拥有者ID
     */
    @NotNull(message = "拥有者ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ownerId;

    /**
     * 封面图
     */
    private String coverImg;

    /**
     * 加入方式（0自由加入 1审核加入 2禁止加入）
     */
    @NotNull(message = "加入方式（0自由加入 1审核加入 2禁止加入）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long joinMode;

    /**
     * 圈子状态 0 启用 1停用
     */
    private Integer status;
}
