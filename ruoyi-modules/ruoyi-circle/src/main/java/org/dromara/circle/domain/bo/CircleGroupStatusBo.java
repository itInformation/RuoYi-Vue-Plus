package org.dromara.circle.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.circle.domain.CircleGroup;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;

/**
 * 圈子主体业务对象 circle_group
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CircleGroup.class, reverseConvertGenerate = false)
public class CircleGroupStatusBo extends BaseEntity {

    /**
     * 圈子ID
     */
    @NotNull(message = "圈子ID不能为空", groups = { EditGroup.class })
    private String groupId;
    /**
     * 圈子状态 0 启用 1停用
     */
    private Integer status;
}
