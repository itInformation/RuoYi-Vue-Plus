package org.dromara.system.domain.bo;

import org.dromara.system.domain.CircleInvite;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 邀请记录业务对象 circle_invite
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CircleInvite.class, reverseConvertGenerate = false)
public class CircleInviteBo extends BaseEntity {

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long inviteId;

    /**
     * 
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long groupId;

    /**
     * 邀请人
     */
    @NotNull(message = "邀请人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long inviterId;

    /**
     * 被邀请人
     */
    @NotNull(message = "被邀请人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long inviteeId;

    /**
     * 状态（0待接受 1已接受 2已拒绝）
     */
    private Long inviteStatus;


}
