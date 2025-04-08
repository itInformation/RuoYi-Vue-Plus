
package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.system.domain.SysUserFollow;
import org.dromara.system.domain.SysUserFriend;
import org.dromara.system.domain.vo.SysUserFollowVo;
import org.dromara.system.domain.vo.SysUserFriendVo;

/**
 * 用户关注关系Mapper接口
 *
 * @author Lion Li
 * @date 2025-04-08
 */
public interface SysUserFriendMapper extends BaseMapperPlus<SysUserFriend, SysUserFriendVo> {
    @Insert("INSERT IGNORE INTO sys_user_friend (user_id, friend_id) " +
        "VALUES (#{userId}, #{friendId})")
    int insertIgnore(@Param("userId") Long userId,
                     @Param("friendId") Long friendId);

    @Delete("DELETE FROM sys_user_friend " +
        "WHERE user_id = #{userId} AND friend_id = #{friendId}")
    int deleteFriend(@Param("userId") Long userId,
                     @Param("friendId") Long friendId);

    @Select("SELECT COUNT(*) FROM sys_user_friend " +
        "WHERE user_id = #{userId} AND friend_id = #{friendId}")
    boolean existsFriend(@Param("userId") Long userId,
                         @Param("friendId") Long friendId);
}
