package org.dromara.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.system.domain.SysUserVip;
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
 * 用户会员信息视图对象 sys_user_vip
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUserVip.class)
public class SysUserVipVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 当前套餐ID
     */
    @ExcelProperty(value = "当前套餐ID")
    private Long planId;

    /**
     * 生效时间
     */
    @ExcelProperty(value = "生效时间")
    private Date startTime;

    /**
     * 过期时间
     */
    @ExcelProperty(value = "过期时间")
    private Date endTime;

    /**
     * 当前等级
     */
    @ExcelProperty(value = "当前等级")
    private Long currentLevel;

    /**
     * 累计经验值
     */
    @ExcelProperty(value = "累计经验值")
    private Long totalExp;

    /**
     * 自动续费（0关闭 1开启）
     */
    @ExcelProperty(value = "自动续费", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=关闭,1=开启")
    private String autoRenew;


}
