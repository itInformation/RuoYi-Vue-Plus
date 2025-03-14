package org.dromara.circle.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.circle.domain.CircleMember;
import org.dromara.circle.domain.vo.CircleMemberVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 用户-圈子关系Mapper接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Mapper
public interface CircleMemberMapper extends BaseMapperPlus<CircleMember, CircleMemberVo> {

}
