package org.dromara.system.domain.bo;

import org.dromara.system.domain.CreatorWithdrawLog;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 创作者提现记录业务对象 creator_withdraw_log
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CreatorWithdrawLog.class, reverseConvertGenerate = false)
public class CreatorWithdrawLogBo extends BaseEntity {

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
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long amount;

    /**
     * 状态(0待审核 1已打款 2已拒绝)
     */
    private Integer status;

    /**
     *
     */
    private String remark;


}
