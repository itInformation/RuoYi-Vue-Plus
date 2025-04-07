package org.dromara.circle.domain.vo;

import org.dromara.circle.domain.CircleGroup;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 圈子主体视图对象 circle_group
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleGroup.class)
public class CircleGroupVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 圈子ID
     */
    @ExcelProperty(value = "圈子ID")
    private String groupId;

    /**
     * 圈子名称
     */
    @ExcelProperty(value = "圈子名称")
    private String groupName;

    /**
     * 圈子描述
     */
    @ExcelProperty(value = "圈子描述")
    private String description;

    /**
     * 拥有者ID
     */
    @ExcelProperty(value = "拥有者ID")
    private Long ownerId;

    /**
     * 封面图
     */
    @ExcelProperty(value = "封面图")
    private String coverImg;

    /**
     * 加入方式（0自由加入 1审核加入 2禁止加入）
     */
    @ExcelProperty(value = "加入方式", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=免费,1=收费")
    private Long joinMode;

    /**
     * 圈子状态 0 启用 1停用
     */
    private Integer status;


    /**
     * 0 不在回收站 1.在回收站
     */
    private Integer recycleBin;
}
