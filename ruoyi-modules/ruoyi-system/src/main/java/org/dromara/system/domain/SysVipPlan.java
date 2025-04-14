package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 会员套餐对象 sys_vip_plan
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_vip_plan")
public class SysVipPlan extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 套餐ID
     */
    @TableId(value = "plan_id")
    private Long planId;

    /**
     * 套餐名称
     */
    private String planName;

    /**
     * 有效期天数
     */
    private Long duration;

    /**
     * 原始价格
     */
    private Long originPrice;

    /**
     * 实际价格
     */
    private Long realPrice;

    /**
     * 要求最低等级
     */
    private Long levelRequire;

    /**
     * 续费折扣
     */
    private Long renewDiscount;

    /**
     * 特权配置
     */
    private String privileges;

    /**
     * 状态（0启用 1停用）
     */
    private String status;


}
