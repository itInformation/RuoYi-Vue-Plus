package org.dromara.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.system.domain.SysVipLevelUser;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 用户等级视图对象 sys_vip_level_user
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysVipLevelUser.class)
public class SysVipLevelUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 类型id
     */
    @ExcelProperty(value = "类型id")
    private Long typeId;

    /**
     * 当前等级
     */
    @ExcelProperty(value = "当前等级")
    private String currentLevel;

    /**
     * 过期时间
     */
    @ExcelProperty(value = "过期时间")
    private Date expireTime;

    /**
     * 圈子id
     */
    @ExcelProperty(value = "圈子id")
    private String circleId;

    /**
     * 创作者id
     */
    @ExcelProperty(value = "创作者id")
    private Long creatorId;

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
