package org.dromara.system.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 邀请记录对象 circle_invite
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("circle_invite")
public class CircleInvite extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "invite_id")
    private Long inviteId;

    /**
     * 
     */
    private Long groupId;

    /**
     * 邀请人
     */
    private Long inviterId;

    /**
     * 被邀请人
     */
    private Long inviteeId;

    /**
     * 状态（0待接受 1已接受 2已拒绝）
     */
    private Long inviteStatus;


}
