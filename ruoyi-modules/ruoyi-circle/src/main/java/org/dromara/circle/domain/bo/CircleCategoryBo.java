package org.dromara.circle.domain.bo;

import org.dromara.circle.domain.CircleCategory;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 圈子分类业务对象 circle_category
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CircleCategory.class, reverseConvertGenerate = false)
public class CircleCategoryBo extends BaseEntity {

    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空", groups = { EditGroup.class })
    private Long catId;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String catName;

    /**
     * 排序
     */
    private Long sortOrder;

    /**
     * 层级
     */
    private Long level;

    /**
     * 是否热门分类
     */
    private Long isHot;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 数据删除状态 0 未删除 1.删除 2.放入回收站
     */
    private Long deleted;


}
