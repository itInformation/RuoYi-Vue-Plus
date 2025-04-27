package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 创作者提现记录对象 creator_withdraw_log
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("creator_withdraw_log")
public class CreatorWithdrawLog extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    /**
     *
     */
    private Long userId;

    /**
     *
     */
    private BigDecimal amount;

    /**
     * 状态(0待审核 1已打款 2已拒绝)
     */
    private Integer status;

    /**
     *
     */
    private String remark;

    /**
     * 乐观锁版本号
     */
    @Version
    private Long version;
}
