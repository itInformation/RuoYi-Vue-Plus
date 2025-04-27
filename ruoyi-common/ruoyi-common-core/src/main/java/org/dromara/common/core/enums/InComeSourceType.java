package org.dromara.common.core.enums;

/**
 * @description: 收入来源类型
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/4/25 14:48
 */
public enum InComeSourceType {
    COURSE_SALE("COURSE_SALE", "课程销售"),
    AD_REVENUE("AD_REVENUE", "广告分成"),
    SUBSCRIPTION("SUBSCRIPTION", "订阅收入"),
    TIP("TIP", "用户打赏"),
    REFUND("REFUND", "退款返还"),
    COMMISSION("COMMISSION", "分销佣金"),
    PROMOTION("PROMOTION", "活动奖励"),
    ROYALTY("ROYALTY", "版权收益"),
    WITHDRAW_REJECT("WITHDRAW_REJECT", "提现退回"),
    OTHER("OTHER", "其他收入");

    private final String code;
    private final String description;

    InComeSourceType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据编码获取枚举（忽略大小写）
     */
    public static InComeSourceType fromCode(String code) {
        for (InComeSourceType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        return OTHER; // 未知类型返回OTHER
    }

    /**
     * 判断是否为正向收入（排除退款类操作）
     */
    public boolean isPositiveIncome() {
        return this != REFUND && this != WITHDRAW_REJECT;
    }
}
