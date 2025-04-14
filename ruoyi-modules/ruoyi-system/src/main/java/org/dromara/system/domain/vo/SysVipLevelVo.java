package org.dromara.system.domain.vo;

import org.dromara.system.domain.SysVipLevel;
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
 * 会员等级视图对象 sys_vip_level
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysVipLevel.class)
public class SysVipLevelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 等级ID
     */
    @ExcelProperty(value = "等级ID")
    private Long levelId;

    /**
     * 等级名称
     */
    @ExcelProperty(value = "等级名称")
    private String levelName;

    /**
     * 最低经验值
     */
    @ExcelProperty(value = "最低经验值")
    private Long minExp;

    /**
     * 最高经验值
     */
    @ExcelProperty(value = "最高经验值")
    private Long maxExp;

    /**
     * 等级图标
     */
    @ExcelProperty(value = "等级图标")
    private String icon;

    /**
     * 等级颜色代码
     */
    @ExcelProperty(value = "等级颜色代码")
    private String colorCode;

    /**
     * 等级特权
     */
    @ExcelProperty(value = "等级特权")
    private String privileges;


}
