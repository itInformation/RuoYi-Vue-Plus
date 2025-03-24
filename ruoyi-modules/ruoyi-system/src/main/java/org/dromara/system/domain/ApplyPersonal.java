package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 个人入驻申请对象 apply_personal
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("apply_personal")
public class ApplyPersonal extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "apply_id")
    private Long applyId;

    /**
     * 
     */
    private String nickname;

    /**
     * ["抖音","快手"]
     */
    private String mainPlatforms;

    /**
     * 
     */
    private String contactInfo;

    /**
     * 
     */
    private String screenshotUrls;


}
