package org.dromara.pay.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;

import java.math.BigDecimal;

/**
 * 支付订单业务对象 pay_order
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Data

public class RefundBo {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderId;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 退款金额
     */
    @NotNull(message = "退款金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal refundAmount;


    /**
     * 退款原因
     */
    private String reason;






}
