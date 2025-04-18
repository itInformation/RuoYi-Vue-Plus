package org.dromara.system.domain.bo;

import org.dromara.system.domain.CreatorAsset;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 创作者资产业务对象 creator_asset
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CreatorAsset.class, reverseConvertGenerate = false)
public class CreatorAssetBo extends BaseEntity {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { EditGroup.class })
    private Long userId;

    /**
     * 可提现金额
     */
    private Long withdrawable;

    /**
     * 冻结可提现
     */
    private Long frozenWithdrawable;

    /**
     * 待入账金额
     */
    private Long pendingAmount;

    /**
     * 冻结待入账
     */
    private Long frozenPending;

    /**
     * 累计提现金额
     */
    private Long totalWithdrawn;

    /**
     * 累计收益金额
     */
    private Long totalIncome;

    /**
     * 累计退款金额
     */
    private Long totalRefund;


}
