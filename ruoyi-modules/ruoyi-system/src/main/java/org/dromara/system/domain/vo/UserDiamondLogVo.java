package org.dromara.system.domain.vo;

import org.dromara.system.domain.UserDiamondLog;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 用户钻石流水视图对象 user_diamond_log
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = UserDiamondLog.class)
public class UserDiamondLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long userId;

    /**
     * 操作类型(RECHARGE/FREEZE/UNFREEZE/CONSUME/REFUND)
     */
    @ExcelProperty(value = "操作类型(RECHARGE/FREEZE/UNFREEZE/CONSUME/REFUND)")
    private String opType;

    /**
     * 操作金额
     */
    @ExcelProperty(value = "")
    private Long amount;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String remark;

    /**
     * 操作前余额
     */
    private Long beforeBalance;
    /**
     * 操作后余额
     */
    private Long afterBalance;

}
