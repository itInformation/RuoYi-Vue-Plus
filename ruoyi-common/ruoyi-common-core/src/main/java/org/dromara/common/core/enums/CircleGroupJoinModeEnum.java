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
public enum CircleGroupJoinModeEnum {

    /**
     * 自由加入
     */
    FREE("0", "自由加入"),
    /**
     * 审核加入
     */
    MEMBER("1", "审核加入"),
    /**
     * 禁止加入
     */
    REFER("2", "禁止加入");

    private final String code;
    private final String info;
}
