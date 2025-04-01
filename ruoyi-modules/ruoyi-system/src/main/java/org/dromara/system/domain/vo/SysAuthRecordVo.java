package org.dromara.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.system.domain.SysAuthRecord;
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
 * 【请填写功能名称】视图对象 sys_auth_record
 *
 * @author Lion Li
 * @date 2025-04-01
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysAuthRecord.class)
public class SysAuthRecordVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @ExcelProperty(value = "记录ID")
    private Long recordId;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 认证类型（FACE人脸）
     */
    @ExcelProperty(value = "认证类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "F=ACE人脸")
    private String authType;

    /**
     * 认证结果（0成功 1失败）
     */
    @ExcelProperty(value = "认证结果", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=成功,1=失败")
    private String result;

    /**
     * 比对分数
     */
    @ExcelProperty(value = "比对分数")
    private Long score;

    /**
     * 失败原因
     */
    @ExcelProperty(value = "失败原因")
    private String reason;

    /**
     * 认证时间
     */
    @ExcelProperty(value = "认证时间")
    private Date authTime;


}
