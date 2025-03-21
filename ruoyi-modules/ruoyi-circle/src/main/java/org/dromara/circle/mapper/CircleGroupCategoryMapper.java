package org.dromara.circle.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.dromara.circle.domain.CircleGroupCategory;
import org.dromara.circle.domain.vo.CircleGroupCategoryVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 圈子-分类关系Mapper接口
 *
 * @author Lion Li
 * @date 2025-03-20
 */
public interface CircleGroupCategoryMapper extends BaseMapperPlus<CircleGroupCategory, CircleGroupCategoryVo> {

    /**
     * 统计分类下的关联圈子数量
     * @param catId 分类ID
     * @return 关联的圈子总数
     */
    @Select("SELECT COUNT(*) FROM circle_group_category WHERE cat_id = #{catId}")
    int countGroups(@Param("catId") Long catId);
}
