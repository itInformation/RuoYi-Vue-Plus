package org.dromara.system.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 支付订单对象 pay_order
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pay_order")
public class PayOrder extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "order_id")
    private Long orderId;

    /**
     * 商户订单号
     */
    private String orderNo;

    /**
     * 支付渠道(wxpay/alipay)
     */
    private String channel;

    /**
     * 支付金额(元)
     */
    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    /**
     * 支付状态
     */
    private String status;

    /**
     * 订单标题
     */
    private String subject;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 异步通知URL
     */
    private String notifyUrl;


}
