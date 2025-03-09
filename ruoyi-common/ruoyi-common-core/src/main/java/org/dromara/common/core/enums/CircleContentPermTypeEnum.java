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
public enum CircleContentPermTypeEnum {

    /**
     * 免费
     */
    FREE("0", "免费"),
    /**
     * 会员
     */
    MEMBER("1", "会员"),
    /**
     * 指定用户校验
     */
    REFER("2", "指定用户校验");

    private final String code;
    private final String info;
}
