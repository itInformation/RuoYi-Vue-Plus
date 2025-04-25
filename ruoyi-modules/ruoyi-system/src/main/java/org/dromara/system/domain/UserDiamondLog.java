package org.dromara.system.domain;

import lombok.experimental.Accessors;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户钻石流水对象 user_diamond_log
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_diamond_log")
@Accessors(chain = true)
public class UserDiamondLog extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    /**
     *
     */
    private Long userId;

    /**
     * 操作类型(RECHARGE/FREEZE/UNFREEZE/CONSUME/REFUND)
     */
    private String opType;

    /**
     * 操作金额
     */
    private Long amount;
    /**
     * 操作前余额
     */
    private Long beforeBalance;
    /**
     * 操作后余额
     */
    private Long afterBalance;

    /**
     *
     */
    private String remark;


}
