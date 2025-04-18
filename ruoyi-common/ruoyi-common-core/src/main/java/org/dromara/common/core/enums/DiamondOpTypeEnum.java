package org.dromara.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 钻石消费类型枚举
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/4/18 10:00
 */
@Getter
@AllArgsConstructor
public enum DiamondOpTypeEnum {


    /**
     * 充值
     */
    RECHARGE("recharge"),

    /**
     * 冻结
     */
    FREEZE("freeze"),

    /**
     * 解冻
     */
    UNFREEZE("unfreeze"),

    /**
     * 消费
     */
    CONSUME("consume"),
    /**
     * 退款
     */
    REFUND("refund");

    private final String operationType;
}
