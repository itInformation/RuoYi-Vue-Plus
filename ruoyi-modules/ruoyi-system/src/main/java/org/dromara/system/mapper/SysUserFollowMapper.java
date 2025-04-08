package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.dromara.system.domain.SysUserFollow;
import org.dromara.system.domain.vo.SysUserFollowVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 用户关注关系Mapper接口
 *
 * @author Lion Li
 * @date 2025-04-08
 */
public interface SysUserFollowMapper extends BaseMapperPlus<SysUserFollow, SysUserFollowVo> {
    @Select("SELECT COUNT(*) FROM sys_user_follow WHERE user_id = #{userId}")
    Integer selectFollowingCount(Long userId);

    /**
     * 防重复插入（使用数据库的INSERT IGNORE语法）
     */
    @Insert("<script>" +
        "INSERT IGNORE INTO sys_user_follow (user_id, creator_id, create_time) " +
        "VALUES (#{userId}, #{creatorId}, #{createTime})" +
        "</script>")
    int insertIgnore(SysUserFollow entity);

    @Select("SELECT COUNT(*) FROM sys_user_follow " +
        "WHERE user_id = #{userId} AND creator_id = #{creatorId}")
    boolean existsFollow(@Param("userId") Long userId,
                         @Param("creatorId") Long creatorId);
}
