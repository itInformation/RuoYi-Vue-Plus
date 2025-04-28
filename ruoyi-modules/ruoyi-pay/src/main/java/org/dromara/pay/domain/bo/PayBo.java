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

public class PayBo {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 支付金额
     */
    private BigDecimal amount;






}
