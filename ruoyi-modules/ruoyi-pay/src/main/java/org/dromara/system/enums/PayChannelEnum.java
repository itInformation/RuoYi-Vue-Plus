package org.dromara.system.enums;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:45 2025/3/5
 * modified by
 */
public enum PayChannelEnum {
    ALIPAY("alipay", "支付宝支付"),
    WXPAY("wxpay", "微信支付"),
;
    private final String code;
    private final String desc;

    PayChannelEnum(String code, String desc) {
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
