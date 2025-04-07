package org.dromara.circle.domain.vo;

import org.dromara.circle.domain.CircleReviewLog;
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
 * 圈子审核记录视图对象 circle_review_log
 *
 * @author Lion Li
 * @date 2025-04-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleReviewLog.class)
public class CircleReviewLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @ExcelProperty(value = "分类ID")
    private Long id;

    /**
     * 圈子Id
     */
    @ExcelProperty(value = "圈子Id")
    private Long circleId;

    /**
     * 圈子名称
     */
    @ExcelProperty(value = "圈子名称")
    private String circleName;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private String userId;

    /**
     * 审核结果 0待审核，1 审核成功 2 审核失败
     */
    @ExcelProperty(value = "审核结果 0待审核，1 审核成功 2 审核失败")
    private Long review;

    /**
     * 审核失败原因
     */
    @ExcelProperty(value = "审核失败原因")
    private String memo;

    /**
     * 审核类型 0 圈子 1 动态
     */
    private Integer type;
}
