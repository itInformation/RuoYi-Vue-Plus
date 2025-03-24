package org.dromara.system.domain.bo;

import org.dromara.system.domain.ApplyPersonal;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 个人入驻申请业务对象 apply_personal
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ApplyPersonal.class, reverseConvertGenerate = false)
public class ApplyPersonalBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long applyId;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String nickname;

    /**
     * ["抖音","快手"]
     */
    private String mainPlatforms;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String contactInfo;

    /**
     *
     */
    private String imgUrls;


}
