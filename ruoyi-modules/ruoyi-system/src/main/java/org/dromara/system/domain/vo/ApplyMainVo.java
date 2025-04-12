package org.dromara.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.system.domain.ApplyMain;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.system.domain.bo.ApplyGuildBo;
import org.dromara.system.domain.bo.ApplyPersonalBo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 入驻申请主视图对象 apply_main
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ApplyMain.class)
public class ApplyMainVo implements Serializable {

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
    private Long userId;

    /**
     * 1-个人 2-公会
     */
    @ExcelProperty(value = "1-个人 2-公会")
    private String applyType;

    /**
     * 审核状态 0 待审核 1 审核通过 2 审核失败
     */
    @ExcelProperty(value = "")
    private String status;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Date submitTime;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Date auditTime;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String auditComment;

    /**
     * 达人申请
     */

    private ApplyPersonalVo applyPersonalVo;

    /**
     * 工会申请
     */
    private ApplyGuildVo applyGuildVo;
}
