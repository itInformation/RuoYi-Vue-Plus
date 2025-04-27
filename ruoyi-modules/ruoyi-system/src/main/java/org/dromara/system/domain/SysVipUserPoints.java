package org.dromara.system.domain;

import lombok.experimental.Accessors;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 积分记录对象 sys_vip_user_points
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_vip_user_points")
@Accessors(chain = true)
public class SysVipUserPoints extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 积分id
     */
    @TableId(value = "points_id")
    private Long pointsId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 创作者id
     */
    private Long typeId;

    /**
     * 当前积分
     */
    private Long currentPoints;

    /**
     * 历史积分
     */
    private Long totalPoints;

    /**
     * 创作者id
     */
    private Long creatorId;

    /**
     * 圈子id
     */
    private String circleId;

    /**
     * 乐观锁
     */
    private Long version;



}
