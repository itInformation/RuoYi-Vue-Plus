package org.dromara.circle.enums;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午9:38 2025/3/5
 * modified by
 */
public enum CircleContentAuthTypeEnum {

    FREE("0","免费"),
    MEMBER("1","会员"),
    REFER("2","指定用户"),
        ;

    private String type;
    private String desc;

    CircleContentAuthTypeEnum(String type, String desc) {
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
