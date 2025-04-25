package org.dromara.pay.enums;

import lombok.Getter;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:45 2025/3/5
 * modified by
 */
@Getter
public enum BusinessTypeEnum {
    ORDER("ORD", 1),
    PAYMENT("PAY", 2),
    REFUND("RFD", 3);

    private final String prefix;
    private final int code;

    BusinessTypeEnum(String desc, int code) {
        this.code = code;
        this.prefix = desc;
    }


}
