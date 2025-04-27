package org.dromara.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 会员等级对象 sys_vip_level
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_vip_level")
public class SysVipLevel extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 等级ID
     */
    @TableId(value = "level_id")
    private Long levelId;
    /**
     * 会员类型ID
     */
    private Long typeId;
    /**
     * 等级名称
     */
    private String levelName;

    /**
     * 最低经验值
     */
    private Long minExp;

    /**
     * 最高经验值
     */
    private Long maxExp;

    /**
     * 等级图标
     */
    private String icon;

    /**
     * 等级颜色代码
     */
    private String colorCode;

    /**
     * 等级特权
     */
    private String privileges;
    /**
     * 有效期天数
     */
    private Integer duration;


}
