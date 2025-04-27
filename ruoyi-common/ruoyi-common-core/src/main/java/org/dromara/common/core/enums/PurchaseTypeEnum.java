package org.dromara.common.core.enums;

/**
 * @description: 收入来源类型
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/4/25 14:48
 */
public enum PurchaseTypeEnum {
    CONTENT("content","RMB" ,"购买付费帖子"),
    /**
     * 铁粉权益不是直接买的 是购买付费文章累计消耗多少钱自动升级的
     */
    VIP("vip", "RMB","购买铁粉权益"),
    SVIP("svip", "RMB","购买超粉权益"),
    CIRCLE("circle", "RMB","购买圈子"),
    WECHAT("wechat", "diamond","购买微信"),
    MEMBER("member", "RMB","购买软件会员"),
    OTHER("other", "RMB","其他收入");
    ;

    private String code;
    private String payMethod;
    private String description;

    PurchaseTypeEnum(String code,String payMethod, String description) {
        this.code = code;
        this.payMethod = payMethod;
        this.description = description;
    }

    /**
     * 根据编码获取枚举（忽略大小写）
     */
    public static PurchaseTypeEnum fromCode(String code) {
        for (PurchaseTypeEnum type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        return OTHER;
    }

    public String getCode() {
        return code;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public String getDescription() {
        return description;
    }
}
