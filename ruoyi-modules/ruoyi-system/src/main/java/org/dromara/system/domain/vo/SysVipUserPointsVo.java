package org.dromara.system.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.system.domain.SysVipUserPoints;
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
 * 积分记录视图对象 sys_vip_user_points
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysVipUserPoints.class)
public class SysVipUserPointsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 积分id
     */
    @ExcelProperty(value = "积分id")
    private Long pointsId;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 创作者id
     */
    @ExcelProperty(value = "创作者id")
    private Long typeId;

    /**
     * 当前积分
     */
    @ExcelProperty(value = "当前积分")
    private Long currentPoints;

    /**
     * 历史积分
     */
    @ExcelProperty(value = "历史积分")
    private Long totalPoints;

    /**
     * 创作者id
     */
    @ExcelProperty(value = "创作者id")
    private Long creatorId;

    /**
     * 圈子id
     */
    @ExcelProperty(value = "圈子id")
    private String circleId;

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
