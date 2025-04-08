package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 创作者统计对象 sys_creator_stats
 *
 * @author Lion Li
 * @date 2025-04-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_creator_stats")
public class SysCreatorStats extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id")
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
