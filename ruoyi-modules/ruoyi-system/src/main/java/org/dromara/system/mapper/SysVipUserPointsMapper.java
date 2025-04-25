package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.dromara.system.domain.SysVipUserPoints;
import org.dromara.system.domain.vo.SysVipUserPointsVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 积分记录Mapper接口
 *
 * @author Lion Li
 * @date 2025-04-25
 */
public interface SysVipUserPointsMapper extends BaseMapperPlus<SysVipUserPoints, SysVipUserPointsVo> {

    @Select("select * from sys_vip_user_points where user_id = #{userId} and type_id = #{typeId}")
    SysVipUserPoints selectByUserAndType(Long userId, Long typeId);

    @Update("UPDATE sys_vip_user_points SET " +
        "current_points = #{newCurrent}, " +
        "version = version + 1, " +
        "total_points = #{newTotal} " +
        "WHERE user_id = #{userId} " +
        "AND type_id = #{typeId} " +
        "AND creator_id = #{creatorId} " +
        "AND creator_id = #{circleId} " +
        "AND circle_id = #{circleId} " +
        "AND version = #{oldVersion}")
    int updatePointsCAS(@Param("userId") Long userId,
                        @Param("typeId") Long typeId,
                        @Param("creatorId") Long creatorId,
                        @Param("circleId") String circleId,
                        @Param("newCurrent") Long newCurrent,
                        @Param("newTotal") Long newTotal,
                        @Param("oldVersion") Long oldVersion);
}
