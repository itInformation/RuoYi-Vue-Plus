package org.dromara.circle.domain.bo;

import org.dromara.circle.domain.CircleContent;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 圈子内容业务对象 circle_content
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CircleContent.class, reverseConvertGenerate = false)
public class CircleContentBo extends BaseEntity {

    /**
     * 内容ID
     */
    @NotNull(message = "内容ID不能为空", groups = { EditGroup.class })
    private String contentId;

    /**
     * 所属圈子ID
     */
    @NotNull(message = "所属圈子ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupId;

    /**
     * 作者ID
     */
    @NotNull(message = "作者ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 类型（1视频 2图文 3文字）
     */
    @NotBlank(message = "类型（1视频 2图文 3文字）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String contentType;

    /**
     * 内容（JSON存储）
     */
    @NotBlank(message = "内容（JSON存储）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 权限类型（0免费 1会员 2指定）
     */
    @NotBlank(message = "权限类型（0免费 1会员 2指定）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String permType;
    /**
     * 审核状态（0待审核 1审核通过 2审核不通过）
     */
    private Integer review;

}
