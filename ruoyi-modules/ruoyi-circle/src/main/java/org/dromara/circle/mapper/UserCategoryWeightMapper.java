package org.dromara.circle.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.dromara.circle.domain.UserCategoryWeight;
import org.dromara.circle.domain.vo.UserCategoryWeightVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 用户分类兴趣权重Mapper接口
 *
 * @author Lion Li
 * @date 2025-03-21
 */
public interface UserCategoryWeightMapper extends BaseMapperPlus<UserCategoryWeight, UserCategoryWeightVo> {
    /**
     * 查询用户的分类权重
     * @param userId 用户ID
     * @return 分类权重列表
     */
    @Select("SELECT cat_id, weight FROM user_category_weight WHERE user_id = #{userId}")
    @Results({
        @Result(property = "categoryId", column = "category_id"),
        @Result(property = "weight", column = "weight")
    })
    List<UserCategoryWeight> selectByUser(@Param("userId") Long userId);
}
