package org.dromara.system.service;

import org.dromara.system.domain.vo.CircleInviteVo;
import org.dromara.system.domain.bo.CircleInviteBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 邀请记录Service接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
public interface ICircleInviteService {

    /**
     * 查询邀请记录
     *
     * @param inviteId 主键
     * @return 邀请记录
     */
    CircleInviteVo queryById(Long inviteId);

    /**
     * 分页查询邀请记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 邀请记录分页列表
     */
    TableDataInfo<CircleInviteVo> queryPageList(CircleInviteBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的邀请记录列表
     *
     * @param bo 查询条件
     * @return 邀请记录列表
     */
    List<CircleInviteVo> queryList(CircleInviteBo bo);

    /**
     * 新增邀请记录
     *
     * @param bo 邀请记录
     * @return 是否新增成功
     */
    Boolean insertByBo(CircleInviteBo bo);

    /**
     * 修改邀请记录
     *
     * @param bo 邀请记录
     * @return 是否修改成功
     */
    Boolean updateByBo(CircleInviteBo bo);

    /**
     * 校验并批量删除邀请记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
