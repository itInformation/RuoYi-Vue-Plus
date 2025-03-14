package org.dromara.circle.domain.vo;

import org.dromara.circle.domain.CircleContentPerm;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 内容权限关联视图对象 circle_content_perm
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleContentPerm.class)
public class CircleContentPermVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 内容ID
     */
    @ExcelProperty(value = "内容ID")
    private String contentId;

    /**
     * 允许查看的用户ID
     */
    @ExcelProperty(value = "允许查看的用户ID")
    private Long userId;


}
