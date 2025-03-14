package org.dromara.circle.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.circle.domain.CircleGroup;
import org.dromara.circle.domain.vo.CircleGroupVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 圈子主体Mapper接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Mapper
public interface CircleGroupMapper extends BaseMapperPlus<CircleGroup, CircleGroupVo> {

}
