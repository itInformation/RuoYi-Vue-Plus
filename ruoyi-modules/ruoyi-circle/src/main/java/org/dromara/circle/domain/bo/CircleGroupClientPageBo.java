package org.dromara.circle.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 圈子主体业务对象 circle_group
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
public class CircleGroupClientPageBo{

    /**
     * 圈子ID
     */
    @NotNull(message = "圈子ID不能为空")
    private String groupId;

    /**
     * 圈子名称
     */
    @NotBlank(message = "圈子名称不能为空")
    private String groupName;


}
