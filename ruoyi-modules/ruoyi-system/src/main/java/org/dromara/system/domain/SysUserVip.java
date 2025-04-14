package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 用户会员信息对象 sys_user_vip
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_vip")
public class SysUserVip extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 当前套餐ID
     */
    private Long planId;

    /**
     * 生效时间
     */
    private Date startTime;

    /**
     * 过期时间
     */
    private Date endTime;

    /**
     * 当前等级
     */
    private Long currentLevel;

    /**
     * 累计经验值
     */
    private Long totalExp;

    /**
     * 自动续费（0关闭 1开启）
     */
    private String autoRenew;


}
