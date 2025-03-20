package org.dromara.circle.domain.vo;

import org.dromara.circle.domain.CircleCategory;
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
 * 圈子分类视图对象 circle_category
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleCategory.class)
public class CircleCategoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @ExcelProperty(value = "分类ID")
    private Long catId;

    /**
     * 父分类ID
     */
    @ExcelProperty(value = "父分类ID")
    private Long parentId;

    /**
     * 分类名称
     */
    @ExcelProperty(value = "分类名称")
    private String catName;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long sortOrder;

    /**
     * 层级
     */
    @ExcelProperty(value = "层级")
    private Long level;

    /**
     * 是否热门分类
     */
    @ExcelProperty(value = "是否热门分类")
    private Long isHot;

    /**
     * 分类图标
     */
    @ExcelProperty(value = "分类图标")
    private String icon;

    /**
     * 数据删除状态 0 未删除 1.删除 2.放入回收站
     */
    @ExcelProperty(value = "数据删除状态 0 未删除 1.删除 2.放入回收站")
    private Long deleted;


}
