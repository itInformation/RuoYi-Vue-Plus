package org.dromara.circle.domain.bo;

import org.dromara.circle.domain.CircleMember;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;

/**
 * 用户-圈子关系业务对象 circle_member
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CircleMember.class, reverseConvertGenerate = false)
public class CircleMemberBo extends BaseEntity {

    /**
     * 关系记录ID
     */
    @NotNull(message = "关系记录ID不能为空", groups = { EditGroup.class })
    private String memberId;

    /**
     * 圈子ID
     */
    @NotNull(message = "圈子ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String groupId;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 角色类型（0普通成员 1管理员 2拥有者）
     */
    @NotNull(message = "角色类型（0普通成员 1管理员 2拥有者）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long roleType;

    /**
     * 状态（0待审核 1正常 2已退出）
     */
    @NotNull(message = "状态（0待审核 1正常 2已退出）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long memberStatus;

    /**
     * 加入时间
     */
    @NotNull(message = "加入时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date joinTime;

    /**
     * 邀请人ID（用于邀请加入场景）
     */
    private Long inviteUser;

    /**
     * 最后活跃时间
     */
    private Date lastActive;


}
