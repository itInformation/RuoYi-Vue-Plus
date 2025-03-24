package org.dromara.common.sms.enums;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午9:38 2025/3/5
 * modified by
 */
public enum SupplierTypeEnum {

    QINIU("qiniu","七牛云"),
    ALIBABA("alibaba","阿里云"),
        ;

    private String type;
    private String desc;

    SupplierTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
