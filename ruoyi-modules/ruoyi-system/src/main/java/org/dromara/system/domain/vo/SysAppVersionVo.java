package org.dromara.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.system.domain.SysAppVersion;
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
 * App版本信息视图对象 sys_app_version
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysAppVersion.class)
public class SysAppVersionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelProperty(value = "主键ID")
    private Long id;

    /**
     * 版本号
     */
    @ExcelProperty(value = "版本号")
    private String versionNumber;

    /**
     * 发布日期
     */
    @ExcelProperty(value = "发布日期")
    private Date releaseDate;

    /**
     * 版本描述
     */
    @ExcelProperty(value = "版本描述")
    private String description;

    /**
     * 下载地址
     */
    @ExcelProperty(value = "下载地址")
    private String downloadUrl;


}
