package org.dromara.system.mapper;

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
}
