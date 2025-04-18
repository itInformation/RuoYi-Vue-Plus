package org.dromara.system.domain.bo;

import org.dromara.system.domain.UserAsset;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 普通用户资产业务对象 user_asset
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = UserAsset.class, reverseConvertGenerate = false)
public class UserAssetBo extends BaseEntity {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { EditGroup.class })
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


}
