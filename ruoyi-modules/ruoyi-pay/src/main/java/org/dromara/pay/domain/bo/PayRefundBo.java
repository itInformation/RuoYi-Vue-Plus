package org.dromara.pay.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.pay.domain.PayRefund;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 退款记录业务对象 pay_refund
 *
 * @author Lion Li
 * @date 2025-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PayRefund.class, reverseConvertGenerate = false)
public class PayRefundBo extends BaseEntity {

    /**
     * 退款ID
     */
    @NotNull(message = "退款ID不能为空", groups = { EditGroup.class })
    private Long refundId;

    /**
     * 退款单号
     */
    private String refundNo;

    /**
     * 原订单号
     */
    private String orderNo;

    /**
     * 交易号
     */
    private String tradeNo;

    /**
     * 退款金额
     */
    private BigDecimal amount;

    /**
     * 退款原因
     */
    private String reason;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 状态 PROCESSING  SUCCESS FAILURE
     */
    private String status;



}
