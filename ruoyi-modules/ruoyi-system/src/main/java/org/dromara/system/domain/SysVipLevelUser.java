package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 用户等级对象 sys_vip_level_user
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_vip_level_user")
public class SysVipLevelUser extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 类型id
     */
    private Long typeId;

    /**
     * 当前等级
     */
    private String currentLevel;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 圈子id
     */
    private String circleId;

    /**
     * 创作者id
     */
    private Long creatorId;

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
