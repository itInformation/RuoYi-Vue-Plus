package org.dromara.circle.domain.vo;

import org.dromara.circle.domain.CircleAuditLog;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 【请填写功能名称】视图对象 circle_audit_log
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleAuditLog.class)
public class CircleAuditLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String logId;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String groupId;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long userId;

    /**
     * 操作类型
     */
    @ExcelProperty(value = "操作类型")
    private String actionType;

    /**
     * 操作对象ID
     */
    @ExcelProperty(value = "操作对象ID")
    private Long targetId;

    /**
     * 详情
     */
    @ExcelProperty(value = "详情")
    private String detail;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String ipAddress;


}
