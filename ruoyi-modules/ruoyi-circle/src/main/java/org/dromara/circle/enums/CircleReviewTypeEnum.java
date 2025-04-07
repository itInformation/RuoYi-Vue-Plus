package org.dromara.circle.enums;

/**
 * description:
 * 圈子内容状态
 * @author minghuiZhang
 * @date created in 下午9:38 2025/3/5
 * modified by
 */
public enum CircleReviewTypeEnum {

    CIRCLE_GROUP(0,"圈子审核"),
    CIRCLE_CONTENT(1,"动态审核"),
        ;

    private Integer type;
    private String desc;

    CircleReviewTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
