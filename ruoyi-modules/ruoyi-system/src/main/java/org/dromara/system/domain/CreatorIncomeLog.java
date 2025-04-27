package org.dromara.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dromara.common.tenant.core.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 创作者收入明细对象 creator_income_log
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("creator_income_log")
@Accessors(chain = true)
public class CreatorIncomeLog extends TenantEntity {

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
     * 来源类型
     */
    private String sourceType;

    /**
     *
     */
    private BigDecimal amount;

    /**
     * 操作前余额
     */
    private Long beforeBalance;
    /**
     * 操作后余额
     */
    private Long afterBalance;


    /**
     * 关联订单号
     */
    private String orderNo;


}
