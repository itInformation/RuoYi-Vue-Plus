package org.dromara.circle.domain.bo;

import org.dromara.circle.domain.CircleResource;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 资源文件业务对象 circle_resource
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CircleResource.class, reverseConvertGenerate = false)
public class CircleResourceBo extends BaseEntity {

    /**
     * 资源ID
     */
    @NotBlank(message = "资源ID不能为空", groups = { EditGroup.class })
    private String resourceId;

    /**
     * 关联内容ID
     */
    @NotNull(message = "关联内容ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String contentId;

    /**
     * 文件类型
     */
    @NotBlank(message = "文件类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fileType;

    /**
     * 存储路径
     */
    @NotBlank(message = "存储路径不能为空", groups = { AddGroup.class, EditGroup.class })
    private String filePath;


}
