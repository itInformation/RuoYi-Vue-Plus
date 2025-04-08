package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysCreatorStats;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 创作者统计业务对象 sys_creator_stats
 *
 * @author Lion Li
 * @date 2025-04-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysCreatorStats.class, reverseConvertGenerate = false)
public class SysCreatorStatsBo extends BaseEntity {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { EditGroup.class })
    private Long userId;

    /**
     * 发布内容总数
     */
    private Long contentCount;

    /**
     * 粉丝数量
     */
    private Long fansCount;

    /**
     * 关注创作者数
     */
    private Long followingCount;

    /**
     * 好友数
     */
    private Long friendCount;


}
