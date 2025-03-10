package org.dromara.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.system.domain.PayOrder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 支付订单视图对象 pay_order
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = PayOrder.class)
public class PayOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @ExcelProperty(value = "订单ID")
    private Long orderId;

    /**
     * 商户订单号
     */
    @ExcelProperty(value = "商户订单号")
    private String orderNo;

    /**
     * 支付渠道(wxpay/alipay)
     */
    @ExcelProperty(value = "支付渠道(wxpay/alipay)")
    private String channel;

    /**
     * 支付金额(元)
     */
    @ExcelProperty(value = "支付金额(元)")
    private BigDecimal amount;

    /**
     * 支付状态
     */
    @ExcelProperty(value = "支付状态")
    private String status;

    /**
     * 订单标题
     */
    @ExcelProperty(value = "订单标题")
    private String subject;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 支付时间
     */
    @ExcelProperty(value = "支付时间")
    private Date payTime;

    /**
     * 过期时间
     */
    @ExcelProperty(value = "过期时间")
    private Date expireTime;

    /**
     * 异步通知URL
     */
    @ExcelProperty(value = "异步通知URL")
    private String notifyUrl;


}
