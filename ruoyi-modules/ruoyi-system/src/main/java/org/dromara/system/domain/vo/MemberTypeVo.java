package org.dromara.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.system.domain.MemberType;
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
 * 会员类型;视图对象 member_type
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MemberType.class)
public class MemberTypeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类型ID
     */
    @ExcelProperty(value = "类型ID")
    private Long typeId;

    /**
     * 类型名称（项目/圈子/创作者）
     */
    @ExcelProperty(value = "类型名称", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "项=目/圈子/创作者")
    private String typeName;

    /**
     * 类型描述
     */
    @ExcelProperty(value = "类型描述")
    private String desc;

    /**
     * 乐观锁
     */
    @ExcelProperty(value = "乐观锁")
    private Long revision;

    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新人
     */
    @ExcelProperty(value = "更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间")
    private Date updatedTime;


}
