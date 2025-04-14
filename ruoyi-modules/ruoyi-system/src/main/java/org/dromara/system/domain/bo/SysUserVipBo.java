package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysUserVip;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户会员信息业务对象 sys_user_vip
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysUserVip.class, reverseConvertGenerate = false)
public class SysUserVipBo extends BaseEntity {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { EditGroup.class })
    private Long userId;

    /**
     * 当前套餐ID
     */
    private Long planId;

    /**
     * 生效时间
     */
    private Date startTime;

    /**
     * 过期时间
     */
    private Date endTime;

    /**
     * 当前等级
     */
    private Long currentLevel;

    /**
     * 累计经验值
     */
    private Long totalExp;

    /**
     * 自动续费（0关闭 1开启）
     */
    private String autoRenew;


}
