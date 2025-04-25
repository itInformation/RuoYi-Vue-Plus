package org.dromara.system.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.EditGroup;

/**
 * 普通用户资产钻石充值业务对象 user_asset
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Data
public class UserAssetDiamondBo {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { EditGroup.class })
    private Long userId;

    /**
     * 操作的钻石数量
     */
    private Long diamond;




}
