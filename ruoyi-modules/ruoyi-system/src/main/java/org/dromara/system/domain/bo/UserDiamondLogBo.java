package org.dromara.system.domain.bo;

import org.dromara.system.domain.UserDiamondLog;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户钻石流水业务对象 user_diamond_log
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserDiamondLog.class, reverseConvertGenerate = false)
public class UserDiamondLogBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 操作类型(RECHARGE/FREEZE/UNFREEZE/CONSUME/REFUND)
     */
    private String opType;

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long amount;

    /**
     * 
     */
    private String remark;


}
