package org.dromara.circle.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.dromara.circle.domain.CircleCategory;
import org.dromara.circle.domain.vo.CircleCategoryVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 圈子分类Mapper接口
 *
 * @author Lion Li
 * @date 2025-03-20
 */
public interface CircleCategoryMapper extends BaseMapperPlus<CircleCategory, CircleCategoryVo> {
    @Update("UPDATE circle_category SET group_count = #{count} WHERE cat_id = #{catId}")
    int updateCount(@Param("catId") Long catId, @Param("count") Integer count);
}
