package org.dromara.system.domain.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 创作者提现用户资产业务对象
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
public class CreatorAssetConfirmWithdrawBo {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 提现记录Id
     */
    private Long logId;
}
