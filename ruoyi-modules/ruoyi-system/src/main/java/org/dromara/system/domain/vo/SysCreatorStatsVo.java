package org.dromara.system.domain.vo;

import org.dromara.system.domain.SysCreatorStats;
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
 * 创作者统计视图对象 sys_creator_stats
 *
 * @author Lion Li
 * @date 2025-04-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysCreatorStats.class)
public class SysCreatorStatsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 发布内容总数
     */
    @ExcelProperty(value = "发布内容总数")
    private Long contentCount;

    /**
     * 粉丝数量
     */
    @ExcelProperty(value = "粉丝数量")
    private Long fansCount;

    /**
     * 关注创作者数
     */
    @ExcelProperty(value = "关注创作者数")
    private Long followingCount;

    /**
     * 好友数
     */
    @ExcelProperty(value = "好友数")
    private Long friendCount;


}
