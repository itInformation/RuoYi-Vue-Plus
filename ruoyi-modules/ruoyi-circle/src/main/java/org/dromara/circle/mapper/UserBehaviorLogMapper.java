package org.dromara.circle.mapper;

import org.dromara.circle.domain.UserBehaviorLog;
import org.dromara.circle.domain.vo.UserBehaviorLogVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户行为日志Mapper接口
 *
 * @author Lion Li
 * @date 2025-03-20
 */
public interface UserBehaviorLogMapper extends BaseMapperPlus<UserBehaviorLog, UserBehaviorLogVo> {

    List<UserBehaviorLog> selectRecentViews(Long userId, int i, LocalDateTime localDateTime);
}
