package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysVipLevel;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 会员等级业务对象 sys_vip_level
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysVipLevel.class, reverseConvertGenerate = false)
public class SysVipLevelBo extends BaseEntity {

    /**
     * 等级ID
     */
    @NotNull(message = "等级ID不能为空", groups = { EditGroup.class })
    private Long levelId;

    /**
     * 等级名称
     */
    @NotBlank(message = "等级名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String levelName;

    /**
     * 最低经验值
     */
    @NotNull(message = "最低经验值不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long minExp;

    /**
     * 最高经验值
     */
    @NotNull(message = "最高经验值不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotBlank(message = "等级特权不能为空", groups = { AddGroup.class, EditGroup.class })
    private String privileges;


}
