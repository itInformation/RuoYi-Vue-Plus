package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 公会入驻申请对象 apply_guild
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("apply_guild")
public class ApplyGuild extends TenantEntity {

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
    private String guildName;

    /**
     * 
     */
    private String contactPerson;

    /**
     * 
     */
    private String contactInfo;

    /**
     * 
     */
    private Long memberCount;

    /**
     * 
     */
    private String cooperatedPlatforms;

    /**
     * 
     */
    private String screenshotUrls;


}
