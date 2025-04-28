package org.dromara.pay.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.pay.domain.PayOrder;

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
public class PayAddOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @ExcelProperty(value = "订单ID")
    private String orderId;

}
