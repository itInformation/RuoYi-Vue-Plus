package org.dromara.system.domain.vo;

import org.dromara.system.domain.CreatorWithdrawLog;
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
 * 创作者提现记录视图对象 creator_withdraw_log
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CreatorWithdrawLog.class)
public class CreatorWithdrawLogVo implements Serializable {

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
     *
     */
    @ExcelProperty(value = "")
    private Long amount;

    /**
     * 状态(0待审核 1已打款 2已拒绝)
     */
    @ExcelProperty(value = "状态(0待审核 1已打款 2已拒绝)")
    private Integer status;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String remark;


}
