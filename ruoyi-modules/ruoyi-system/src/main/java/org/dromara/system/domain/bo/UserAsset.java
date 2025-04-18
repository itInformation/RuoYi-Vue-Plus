package org.dromara.system.domain.bo;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 普通用户资产对象 user_asset
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_asset")
public class UserAsset extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 钻石余额
     */
    private Long diamondBalance;

    /**
     * 冻结钻石
     */
    private Long frozenDiamond;

    /**
     * 累计消费钻石
     */
    private Long totalConsumedDiamond;

    /**
     * 累计消费金额
     */
    private Long totalConsumedAmount;

    /**
     * 累计退款金额
     */
    private Long totalRefundAmount;

    /**
     * 乐观锁版本号
     */
    @Version
    private Long version;


}
