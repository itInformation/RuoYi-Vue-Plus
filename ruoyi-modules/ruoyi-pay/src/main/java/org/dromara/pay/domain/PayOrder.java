package org.dromara.pay.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

import java.io.Serial;

/**
 * 支付订单对象 pay_order
 *字段	orderId（主键ID）	orderNo（商户订单号）
 * 生成方	支付系统内部生成（如自增ID、雪花算法ID）	商户/业务系统生成
 * 唯一性	全局唯一（数据库主键保证）	业务唯一（需商户自行保证）
 * 可见性	内部使用（不暴露给外部）	暴露给商户和第三方支付平台（如微信/支付宝）
 * 用途	系统内部关联查询（如数据库JOIN）	商户对账、第三方支付回调、幂等性控制
 * 数据结构	通常为数值类型（BIGINT）	字符串类型（符合第三方规范，如"20230801123456"）
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
