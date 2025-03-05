package org.dromara.system.domain.bo;

import org.dromara.system.domain.CircleContentPerm;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 内容权限关联业务对象 circle_content_perm
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CircleContentPerm.class, reverseConvertGenerate = false)
public class CircleContentPermBo extends BaseEntity {

    /**
     * 内容ID
     */
    @NotNull(message = "内容ID不能为空", groups = { EditGroup.class })
    private Long contentId;

    /**
     * 允许查看的用户ID
     */
    @NotNull(message = "允许查看的用户ID不能为空", groups = { EditGroup.class })
    private Long userId;


}
