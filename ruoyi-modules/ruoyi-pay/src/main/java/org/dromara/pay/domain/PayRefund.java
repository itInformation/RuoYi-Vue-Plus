package org.dromara.pay.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 退款记录对象 pay_refund
 *
 * @author Lion Li
 * @date 2025-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pay_refund")
public class PayRefund extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 退款ID
     */
    @TableId(value = "refund_id")
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
    private Long amount;

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

    /**
     * 乐观锁
     */
    @Version
    private Long version;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;
    /**
     * 用户ID
     */
    private Long userId;

}
