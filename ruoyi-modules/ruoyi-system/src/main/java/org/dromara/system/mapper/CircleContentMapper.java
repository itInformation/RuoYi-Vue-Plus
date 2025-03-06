package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.system.domain.CircleContent;
import org.dromara.system.domain.vo.CircleContentVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 圈子内容Mapper接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Mapper
public interface CircleContentMapper extends BaseMapperPlus<CircleContent, CircleContentVo> {

}
