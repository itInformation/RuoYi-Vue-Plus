package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysUserFollow;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户关注关系业务对象 sys_user_follow
 *
 * @author Lion Li
 * @date 2025-04-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysUserFollow.class, reverseConvertGenerate = false)
public class SysUserFollowBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 关注者ID
     */
    @NotNull(message = "关注者ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 被关注创作者ID
     */
    @NotNull(message = "被关注创作者ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long creatorId;


}
