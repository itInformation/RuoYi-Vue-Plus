package org.dromara.circle.domain.vo;

import org.dromara.circle.domain.CircleGroupCategory;
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
 * 圈子-分类关系视图对象 circle_group_category
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleGroupCategory.class)
public class CircleGroupCategoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 圈子ID
     */
    @ExcelProperty(value = "圈子ID")
    private Long groupId;

    /**
     * 分类ID
     */
    @ExcelProperty(value = "分类ID")
    private Long catId;

    /**
     * 数据删除状态 0 未删除 1.删除 2.放入回收站
     */
    @ExcelProperty(value = "数据删除状态 0 未删除 1.删除 2.放入回收站")
    private Long deleted;


}
