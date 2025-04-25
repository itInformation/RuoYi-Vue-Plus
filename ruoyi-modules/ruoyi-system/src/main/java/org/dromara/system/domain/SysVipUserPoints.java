package org.dromara.system.domain;

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
public class SysVipUserPoints extends TenantEntity {

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
    private Long revision;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;


}
