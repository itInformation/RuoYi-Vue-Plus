package org.dromara.pay.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.pay.domain.PayRefund;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 退款记录视图对象 pay_refund
 *
 * @author Lion Li
 * @date 2025-04-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = PayRefund.class)
public class PayRefundVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 退款ID
     */
    @ExcelProperty(value = "退款ID")
    private Long refundId;

    /**
     * 退款单号
     */
    @ExcelProperty(value = "退款单号")
    private String refundNo;

    /**
     * 原订单号
     */
    @ExcelProperty(value = "原订单号")
    private String orderNo;

    /**
     * 交易号
     */
    @ExcelProperty(value = "交易号")
    private String tradeNo;

    /**
     * 退款金额
     */
    @ExcelProperty(value = "退款金额")
    private Long amount;

    /**
     * 退款原因
     */
    @ExcelProperty(value = "退款原因")
    private String reason;

    /**
     * 完成时间
     */
    @ExcelProperty(value = "完成时间")
    private Date finishTime;

    /**
     * 状态 PROCESSING  SUCCESS FAILURE
     */
    @ExcelProperty(value = "状态 PROCESSING  SUCCESS FAILURE")
    private String status;




}
