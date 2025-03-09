package org.dromara.system.enums;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:45 2025/3/5
 * modified by
 */
public enum CircleRoleEnum {
    NORMAL(0, "普通成员"),
    ADMIN(1, "管理员"),
    OWNER(2, "拥有者");

    private final int code;
    private final String desc;

    CircleRoleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
