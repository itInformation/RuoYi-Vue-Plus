package org.dromara.system.domain.vo;

import org.dromara.system.domain.ApplyGuild;
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
 * 公会入驻申请视图对象 apply_guild
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ApplyGuild.class)
public class ApplyGuildVo implements Serializable {

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
    private String guildName;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String contactPerson;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String contactInfo;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long memberCount;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String platforms;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String imgUrls;


}
