package org.dromara.circle.domain.vo;

import org.dromara.circle.domain.UserBehaviorLog;
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
 * 用户行为日志视图对象 user_behavior_log
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = UserBehaviorLog.class)
public class UserBehaviorLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long logId;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 圈子ID
     */
    @ExcelProperty(value = "圈子ID")
    private String groupId;

    /**
     * 关联分类ID列表
     */
    @ExcelProperty(value = "关联分类ID列表")
    private String catIds;

    /**
     * 行为类型（1浏览 2加入 3收藏）
     */
    @ExcelProperty(value = "行为类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=浏览,2=加入,3=收藏")
    private Long actionType;

    /**
     * 数据删除状态 0 未删除 1 删除
     */
    @ExcelProperty(value = "数据删除状态 0 未删除 1 删除")
    private Long deleted;


}
