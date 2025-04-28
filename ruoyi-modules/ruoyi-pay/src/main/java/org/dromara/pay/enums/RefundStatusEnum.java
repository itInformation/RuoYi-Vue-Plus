package org.dromara.pay.enums;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:45 2025/3/5
 * modified by
 */
public enum RefundStatusEnum {
    PROCESSING("PROCESSING", "处理中"),
    SUCCESS("SUCCESS", "支付成功"),
    FAILURE("FAILURE", "支付失败"),
;
    private final String code;
    private final String desc;

    RefundStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
