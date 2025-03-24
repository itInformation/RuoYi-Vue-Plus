package org.dromara.system.domain.bo;

import org.dromara.system.domain.ApplyGuild;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 公会入驻申请业务对象 apply_guild
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ApplyGuild.class, reverseConvertGenerate = false)
public class ApplyGuildBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long applyId;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String guildName;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String contactPerson;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String contactInfo;

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long memberCount;

    /**
     *
     */
    private String platforms;

    /**
     *
     */
    private String imgUrls;


}
