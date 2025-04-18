package org.dromara.system.domain.vo;

import org.dromara.system.domain.CreatorIncomeLog;
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
 * 创作者收入明细视图对象 creator_income_log
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CreatorIncomeLog.class)
public class CreatorIncomeLogVo implements Serializable {

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
     * 来源类型
     */
    @ExcelProperty(value = "来源类型")
    private String sourceType;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long amount;

    /**
     * 关联订单号
     */
    @ExcelProperty(value = "关联订单号")
    private String orderNo;


}
