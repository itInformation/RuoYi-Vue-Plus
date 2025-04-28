package org.dromara.pay.domain.bo;

import org.dromara.pay.domain.PayOrder;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 支付订单业务对象 pay_order
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PayOrder.class, reverseConvertGenerate = false)
public class PayOrderBo extends BaseEntity {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 商户订单号
     */
    private String orderNo;

    /**
     * 支付渠道(wxpay/alipay)
     */
    @NotBlank(message = "支付渠道(wxpay/alipay)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String channel;

    /**
     * 支付金额(元)
     */
    @NotNull(message = "支付金额(元)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long amount;

    /**
     * 支付状态
     */
    private String status;

    /**
     * 订单标题
     */
    @NotBlank(message = "订单标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String subject;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 异步通知URL
     */
    private String notifyUrl;

    /**
     * 支付渠道单号
     */
    private String tradeNo;

    /**
     * 退款金额(元)
     */
    private BigDecimal refundAmount;
}
