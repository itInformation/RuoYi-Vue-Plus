package org.dromara.circle.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.circle.domain.CircleContentPerm;
import org.dromara.circle.domain.vo.CircleContentPermVo;

/**
 * 内容权限关联Mapper接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Mapper
public interface CircleContentPermMapper extends BaseMapperPlus<CircleContentPerm, CircleContentPermVo> {
}
