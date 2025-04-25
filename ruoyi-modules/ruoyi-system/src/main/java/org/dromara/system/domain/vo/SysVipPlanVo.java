package org.dromara.system.domain.vo;

import org.dromara.system.domain.SysVipPlan;
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
 * 会员套餐视图对象 sys_vip_plan
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysVipPlan.class)
public class SysVipPlanVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 类型ID
     */
    @ExcelProperty(value = "类型ID")
    private Long typeId;
    /**
     * 套餐ID
     */
    @ExcelProperty(value = "套餐ID")
    private Long planId;

    /**
     * 套餐名称
     */
    @ExcelProperty(value = "套餐名称")
    private String planName;

    /**
     * 有效期天数
     */
    @ExcelProperty(value = "有效期天数")
    private Long duration;

    /**
     * 原始价格
     */
    @ExcelProperty(value = "原始价格")
    private Long originPrice;

    /**
     * 实际价格
     */
    @ExcelProperty(value = "实际价格")
    private Long realPrice;

    /**
     * 要求最低等级
     */
    @ExcelProperty(value = "要求最低等级")
    private Long levelRequire;

    /**
     * 续费折扣
     */
    @ExcelProperty(value = "续费折扣")
    private Long renewDiscount;

    /**
     * 特权配置
     */
    @ExcelProperty(value = "特权配置")
    private String privileges;

    /**
     * 状态（0启用 1停用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=启用,1=停用")
    private String status;


}
