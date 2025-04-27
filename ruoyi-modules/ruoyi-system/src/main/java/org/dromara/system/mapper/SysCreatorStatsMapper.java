package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.dromara.system.domain.SysCreatorStats;
import org.dromara.system.domain.vo.SysCreatorStatsVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 创作者统计Mapper接口
 *
 * @author Lion Li
 * @date 2025-04-08
 */
public interface SysCreatorStatsMapper extends BaseMapperPlus<SysCreatorStats, SysCreatorStatsVo> {
    @Update("UPDATE sys_creator_stats SET content_count = content_count + 1 WHERE user_id = #{userId}")
    int incrementContentCount(@Param("userId") Long userId);
    @Update("UPDATE sys_creator_stats SET friend_count = friend_count + #{count} WHERE user_id = #{userId}")
    int incrementFriendCount(@Param("userId") Long userId,@Param("count")Integer count);

    @Update("UPDATE sys_creator_stats SET fans_count = fans_count + #{delta} WHERE user_id = #{userId}")
    int updateFansCount(@Param("userId") Long userId, @Param("delta") int delta);
    @Update("UPDATE sys_creator_stats SET following_count = following_count + #{delta} WHERE user_id = #{userId}")
    int updateFollowingCount(@Param("userId") Long userId, @Param("delta") int delta);

}
