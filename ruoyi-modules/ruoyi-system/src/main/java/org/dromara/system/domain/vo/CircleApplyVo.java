package org.dromara.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.system.domain.CircleApply;
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
 * 圈子申请记录视图对象 circle_apply
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleApply.class)
public class CircleApplyVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long applyId;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long groupId;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long userId;

    /**
     * 申请理由
     */
    @ExcelProperty(value = "申请理由")
    private String applyReason;

    /**
     * 状态（0待处理 1通过 2拒绝）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=待处理,1=通过,2=拒绝")
    private Long applyStatus;

    /**
     * 审核人
     */
    @ExcelProperty(value = "审核人")
    private Long auditUser;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Date auditTime;


}
