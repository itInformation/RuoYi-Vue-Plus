package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.system.domain.SysVipLevel;
import org.dromara.system.domain.vo.SysVipLevelVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 会员等级Mapper接口
 *
 * @author Lion Li
 * @date 2025-04-14
 */
public interface SysVipLevelMapper extends BaseMapperPlus<SysVipLevel, SysVipLevelVo> {

    /**
     * 根据类型和当前积分查找匹配的等级规则
     * @param typeId 会员类型ID
     * @param currentPoints 用户当前积分
     * @return 匹配的等级规则，若无返回null
     */
    SysVipLevel findMatchLevel(
        @Param("typeId") Long typeId,
        @Param("currentPoints") Long currentPoints
    );
}
