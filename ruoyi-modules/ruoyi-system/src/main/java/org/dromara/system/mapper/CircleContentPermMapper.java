package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.system.domain.CircleContentPerm;
import org.dromara.system.domain.vo.CircleContentPermVo;

/**
 * 内容权限关联Mapper接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Mapper
public interface CircleContentPermMapper extends BaseMapperPlus<CircleContentPerm, CircleContentPermVo> {
}
