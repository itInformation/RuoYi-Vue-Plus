package org.dromara.circle.service;

import org.dromara.circle.domain.vo.UserBehaviorLogVo;
import org.dromara.circle.domain.bo.UserBehaviorLogBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户行为日志Service接口
 *
 * @author Lion Li
 * @date 2025-03-20
 */
public interface IUserBehaviorLogService {

    /**
     * 查询用户行为日志
     *
     * @param logId 主键
     * @return 用户行为日志
     */
    UserBehaviorLogVo queryById(Long logId);

    /**
     * 分页查询用户行为日志列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户行为日志分页列表
     */
    TableDataInfo<UserBehaviorLogVo> queryPageList(UserBehaviorLogBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户行为日志列表
     *
     * @param bo 查询条件
     * @return 用户行为日志列表
     */
    List<UserBehaviorLogVo> queryList(UserBehaviorLogBo bo);

    /**
     * 新增用户行为日志
     *
     * @param bo 用户行为日志
     * @return 是否新增成功
     */
    Boolean insertByBo(UserBehaviorLogBo bo);

    /**
     * 修改用户行为日志
     *
     * @param bo 用户行为日志
     * @return 是否修改成功
     */
    Boolean updateByBo(UserBehaviorLogBo bo);

    /**
     * 校验并批量删除用户行为日志信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<Long> getViewedCategories(Long userId);
}
