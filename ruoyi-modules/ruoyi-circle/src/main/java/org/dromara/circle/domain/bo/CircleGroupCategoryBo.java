package org.dromara.circle.domain.bo;

import org.dromara.circle.domain.CircleGroupCategory;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 圈子-分类关系业务对象 circle_group_category
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CircleGroupCategory.class, reverseConvertGenerate = false)
public class CircleGroupCategoryBo extends BaseEntity {

    /**
     * 圈子ID
     */
    @NotNull(message = "圈子ID不能为空", groups = { EditGroup.class })
    private String groupId;

    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空", groups = { EditGroup.class })
    private Long catId;

    /**
     * 数据删除状态 0 未删除 1.删除 2.放入回收站
     */
    private Long deleted;


}
