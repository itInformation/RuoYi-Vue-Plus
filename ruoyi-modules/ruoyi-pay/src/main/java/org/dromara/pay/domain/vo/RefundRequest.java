package org.dromara.pay.domain.vo;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:23 2025/4/21
 * modified by
 */
@Data
public class RefundRequest {
    @NotBlank(message = "原订单号不能为空")
    @Size(min = 16, max = 32, message = "订单号长度16-32位")
    private String orderNo;         // 原支付订单号（商户系统）

    @NotBlank(message = "退款单号不能为空")
    @Size(min = 16, max = 32, message = "退款单号长度16-32位")
    private String refundNo;        // 退款单号（商户系统生成）

    @NotNull(message = "退款金额不能为空")
    @DecimalMin(value = "0.01", message = "退款金额最小0.01元")
    @Digits(integer = 10, fraction = 2, message = "金额格式不正确")
    private BigDecimal refundAmount; // 退款金额

    @NotBlank(message = "支付渠道不能为空")
    @Pattern(regexp = "alipay|wxpay", message = "不支持的支付渠道")
    private String channel;         // 支付渠道（alipay/wxpay）
    // endregion

    // region 扩展参数
    @Size(max = 200, message = "退款原因最长200字符")
    private String reason;          // 退款原因（可选）

    private String operator;        // 操作人员（系统自动填充）

    @NotBlank(message = "时间戳不能为空")
    private String timestamp;       // 请求时间戳（防重放）

    @NotBlank(message = "签名不能为空")
    private String sign;            // 参数签名
    // endregion

    // 生成签名方法（示例）
    public String generateSign(String appSecret) {
        String content = String.join("|",
            orderNo,
            refundNo,
            refundAmount.toPlainString(),
            channel,
            timestamp
        );
        return DigestUtils.md5Hex(content + appSecret);
    }
}
