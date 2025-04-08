package org.dromara.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;

/**
 * 用户关注关系对象 sys_user_follow
 *
 * @author Lion Li
 * @date 2025-04-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_friend")
public class SysUserFriend extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 关注者ID
     */
    private Long userId;

    /**
     * 朋友ID
     */
    private Long friendId;

    public SysUserFriend(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public SysUserFriend() {

    }
}
