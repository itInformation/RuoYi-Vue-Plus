package org.dromara.circle.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.circle.domain.UserCategoryWeight;
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
 * 用户分类兴趣权重视图对象 user_category_weight
 *
 * @author Lion Li
 * @date 2025-03-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = UserCategoryWeight.class)
public class UserCategoryWeightVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 分类ID
     */
    @ExcelProperty(value = "分类ID")
    private Long catId;

    /**
     * 权重值
     */
    @ExcelProperty(value = "权重值")
    private Long weight;

    /**
     * 最后活跃时间
     */
    @ExcelProperty(value = "最后活跃时间")
    private Date lastActive;


}
