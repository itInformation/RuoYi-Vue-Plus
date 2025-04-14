package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysVipPlan;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 会员套餐业务对象 sys_vip_plan
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysVipPlan.class, reverseConvertGenerate = false)
public class SysVipPlanBo extends BaseEntity {

    /**
     * 套餐ID
     */
    @NotNull(message = "套餐ID不能为空", groups = { EditGroup.class })
    private Long planId;

    /**
     * 套餐名称
     */
    @NotBlank(message = "套餐名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String planName;

    /**
     * 有效期天数
     */
    @NotNull(message = "有效期天数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long duration;

    /**
     * 原始价格
     */
    @NotNull(message = "原始价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long originPrice;

    /**
     * 实际价格
     */
    @NotNull(message = "实际价格不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotBlank(message = "特权配置不能为空", groups = { AddGroup.class, EditGroup.class })
    private String privileges;

    /**
     * 状态（0启用 1停用）
     */
    private String status;


}
