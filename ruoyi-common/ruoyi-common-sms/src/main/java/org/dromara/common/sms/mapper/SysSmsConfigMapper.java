package org.dromara.common.sms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.common.sms.domain.SysSmsConfig;
import org.dromara.common.sms.domain.vo.SysSmsConfigVo;

/**
 * 短信配置Mapper接口
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Mapper
public interface SysSmsConfigMapper extends BaseMapperPlus<SysSmsConfig, SysSmsConfigVo> {

}
