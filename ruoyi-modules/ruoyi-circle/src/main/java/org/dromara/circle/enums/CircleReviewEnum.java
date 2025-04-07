package org.dromara.circle.enums;

/**
 * description:
 * 圈子内容状态
 * @author minghuiZhang
 * @date created in 下午9:38 2025/3/5
 * modified by
 */
public enum CircleReviewEnum {

    NO_REVIEW(0,"待审核"),
    SUCCESS(1,"审核成功"),
    FAILURE(2,"审核失败"),
        ;

    private Integer type;
    private String desc;

    CircleReviewEnum(Integer type, String desc) {
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
