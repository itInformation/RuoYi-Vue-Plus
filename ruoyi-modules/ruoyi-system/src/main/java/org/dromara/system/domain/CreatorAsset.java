package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 创作者资产对象 creator_asset
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("creator_asset")
public class CreatorAsset extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id")
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

    /**
     * 乐观锁版本号
     */
    @Version
    private Long version;


}
