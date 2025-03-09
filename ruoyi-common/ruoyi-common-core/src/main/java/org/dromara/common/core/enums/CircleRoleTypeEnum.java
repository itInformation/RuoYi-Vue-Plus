package org.dromara.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/6 16:56
 */
@Getter
@AllArgsConstructor
public enum CircleRoleTypeEnum {
    NORMAL(0, "普通成员"),
    ADMIN(1, "管理员"),
    OWNER(2, "拥有者");

    private final Integer code;
    private final String desc;
}
