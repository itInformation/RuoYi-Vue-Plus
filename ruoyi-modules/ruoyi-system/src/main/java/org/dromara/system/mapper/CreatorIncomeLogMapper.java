package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.system.domain.CreatorIncomeLog;
import org.dromara.system.domain.vo.CreatorIncomeLogVo;

import java.util.List;

/**
 * 创作者收入明细Mapper接口
 *
 * @author Lion Li
 * @date 2025-04-18
 */
public interface CreatorIncomeLogMapper extends BaseMapperPlus<CreatorIncomeLog, CreatorIncomeLogVo> {
    @Select("select id,user_id,source_type,amount ,order_no, create_time from creator_income_log where user_id = #{userId} ORDER BY create_time DESC")

    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "sourceType", column = "source_type"),
        @Result(property = "amount", column = "amount"),
        @Result(property = "orderNo", column = "order_no"),
        @Result(property = "userId", column = "create_time"),
    })
    List<CreatorIncomeLogVo> selectIncomeList(@Param("userId") Long userId);

}
