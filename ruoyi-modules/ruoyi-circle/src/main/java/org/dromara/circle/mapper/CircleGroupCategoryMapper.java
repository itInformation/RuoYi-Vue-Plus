package org.dromara.circle.mapper;

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

    void deleteByGroup(Long groupId);
}
