package org.dromara.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/6 16:07
 */
@Getter
@AllArgsConstructor
public enum WithdrawStatusEnum {

    /**
     * 待审核
     */
    PENDING(0, "待审核"),
    /**
     * 已打款
     */
    PAID(1, "已打款"),
    /**
     * 已拒绝
     */
    REFUSE(2, "已拒绝")
    ;

    private final Integer code;
    private final String info;
}
