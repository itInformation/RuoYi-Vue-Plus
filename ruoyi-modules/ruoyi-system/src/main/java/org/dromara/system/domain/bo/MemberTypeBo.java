package org.dromara.system.domain.bo;

import org.dromara.system.domain.MemberType;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 会员类型;业务对象 member_type
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MemberType.class, reverseConvertGenerate = false)
public class MemberTypeBo extends BaseEntity {

    /**
     * 类型ID
     */
    @NotNull(message = "类型ID不能为空", groups = { EditGroup.class })
    private Long typeId;

    /**
     * 类型名称（项目/圈子/创作者）
     */
    @NotBlank(message = "类型名称（项目/圈子/创作者）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String typeName;

    /**
     * 类型描述
     */
    private String desc;

    /**
     * 乐观锁
     */
    private Long revision;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;


}
