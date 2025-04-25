package org.dromara.system.mapper;

import org.dromara.system.domain.SysVipLevelUser;
import org.dromara.system.domain.vo.SysVipLevelUserVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 用户等级Mapper接口
 *
 * @author Lion Li
 * @date 2025-04-25
 */
public interface SysVipLevelUserMapper extends BaseMapperPlus<SysVipLevelUser, SysVipLevelUserVo> {
    /**
     * 插入或更新用户等级信息
     * @param userLevel 用户等级实体
     * @return 受影响行数
     */
    int upsert(SysVipLevelUser userLevel);
}
