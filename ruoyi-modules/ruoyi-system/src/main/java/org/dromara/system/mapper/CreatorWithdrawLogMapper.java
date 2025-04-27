package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.dromara.system.domain.CreatorWithdrawLog;
import org.dromara.system.domain.vo.CreatorWithdrawLogVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.math.BigDecimal;

/**
 * 创作者提现记录Mapper接口
 *
 * @author Lion Li
 * @date 2025-04-18
 */
public interface CreatorWithdrawLogMapper extends BaseMapperPlus<CreatorWithdrawLog, CreatorWithdrawLogVo> {
    @Select("select COALESCE(SUM(amount), 0) from creator_withdraw_log where user_id = #{userId}  AND status = 1 ")
    BigDecimal sumWithdrawn(Long userId);
    @Update("UPDATE creator_withdraw_log SET status = #{newStatus}  ," +
        "version = version + 1 " +
        " WHERE id = #{logId} " +
        "And status = #{oldStatus} " +
        "AND version = #{oldVersion}")
    int casUpdateWithdrawStatus(
        @Param("logId") Long logId,
        @Param("oldStatus") Integer oldStatus,
        @Param("newStatus") Integer newStatus,
        @Param("oldVersion") Long oldVersion
    );
}
