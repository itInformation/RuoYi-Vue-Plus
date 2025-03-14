package org.dromara.circle.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 用户-圈子关系对象 circle_member
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("circle_member")
public class CircleMember extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关系记录ID
     */
    @TableId(value = "member_id", type = IdType.ASSIGN_ID)
    private String memberId;

    /**
     * 圈子ID
     */
    private String groupId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色类型（0普通成员 1管理员 2拥有者）
     */
    private Integer roleType;

    /**
     * 状态（0待审核 1正常 2已退出）
     */
    private Long memberStatus;

    /**
     * 加入时间
     */
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
