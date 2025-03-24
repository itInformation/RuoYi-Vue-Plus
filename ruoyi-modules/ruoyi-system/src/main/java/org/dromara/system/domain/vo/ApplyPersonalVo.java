package org.dromara.system.domain.vo;

import org.dromara.system.domain.ApplyPersonal;
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
 * 个人入驻申请视图对象 apply_personal
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ApplyPersonal.class)
public class ApplyPersonalVo implements Serializable {

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
    private String nickname;

    /**
     * ["抖音","快手"]
     */
    @ExcelProperty(value = "["抖音","快手"]")
    private String mainPlatforms;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private String contactInfo;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private String screenshotUrls;


}
