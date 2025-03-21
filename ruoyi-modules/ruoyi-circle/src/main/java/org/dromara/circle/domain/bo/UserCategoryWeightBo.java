package org.dromara.circle.domain.bo;

import org.dromara.circle.domain.UserCategoryWeight;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户分类兴趣权重业务对象 user_category_weight
 *
 * @author Lion Li
 * @date 2025-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserCategoryWeight.class, reverseConvertGenerate = false)
public class UserCategoryWeightBo extends BaseEntity {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { EditGroup.class })
    private Long userId;

    /**
     * 分类ID
     */
    @NotNull(message = "分类ID不能为空", groups = { EditGroup.class })
    private Long catId;

    /**
     * 权重值
     */
    @NotNull(message = "权重值不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long weight;

    /**
     * 最后活跃时间
     */
    @NotNull(message = "最后活跃时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date lastActive;


}
