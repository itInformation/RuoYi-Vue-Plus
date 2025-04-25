package org.dromara.pay.domain.vo;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:24 2025/4/21
 * modified by
 */
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class RefundResult {

    // region 核心状态
    private boolean success;          // 是否成功
    private String refundNo;          // 退款单号（与请求参数一致）
    private String platformRefundNo;  // 支付平台退款单号
    // endregion

    // region 金额信息
    private BigDecimal refundAmount;  // 申请退款金额
    private BigDecimal actualAmount;  // 实际退款金额（可能部分退款）
    private String currency = "CNY";  // 币种
    // endregion

    // region 错误信息
    private String errorCode;         // 错误代码（如：USER_BALANCE_NOT_ENOUGH）
    private String errorMsg;          // 错误描述（可展示给用户）
    private String internalMsg;       // 内部错误详情（调试用）
    // endregion

    // region 时间信息
    private String requestTime;       // 请求时间（yyyy-MM-dd HH:mm:ss）
    private String completeTime;      // 完成时间（异步通知时更新）
    // endregion

    // 快速创建成功结果
    public static RefundResult success(String refundNo, String platformRefundNo) {
        return new RefundResult()
            .setSuccess(true)
            .setRefundNo(refundNo)
            .setPlatformRefundNo(platformRefundNo);
    }

    // 快速创建失败结果
    public static RefundResult fail(String errorCode, String errorMsg) {
        return new RefundResult()
            .setSuccess(false)
            .setErrorCode(errorCode)
            .setErrorMsg(errorMsg);
    }
}
