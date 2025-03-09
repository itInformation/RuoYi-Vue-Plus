package org.dromara.system.service;

import org.dromara.system.domain.CircleMember;
import org.dromara.system.domain.vo.CircleMemberVo;
import org.dromara.system.domain.bo.CircleMemberBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户-圈子关系Service接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
public interface ICircleMemberService {

    /**
     * 查询用户-圈子关系
     *
     * @param memberId 主键
     * @return 用户-圈子关系
     */
    CircleMemberVo queryById(Long memberId);

    /**
     * 分页查询用户-圈子关系列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户-圈子关系分页列表
     */
    TableDataInfo<CircleMemberVo> queryPageList(CircleMemberBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户-圈子关系列表
     *
     * @param bo 查询条件
     * @return 用户-圈子关系列表
     */
    List<CircleMemberVo> queryList(CircleMemberBo bo);

    /**
     * 新增用户-圈子关系
     *
     * @param bo 用户-圈子关系
     * @return 是否新增成功
     */
    Boolean insertByBo(CircleMemberBo bo);

    /**
     * 修改用户-圈子关系
     *
     * @param bo 用户-圈子关系
     * @return 是否修改成功
     */
    Boolean updateByBo(CircleMemberBo bo);

    /**
     * 校验并批量删除用户-圈子关系信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 判断用户是否在圈子中
     * @param groupId
     * @param userId
     * @return
     */
    Integer existInGroup(Long groupId,Long userId);
    CircleMember selectByGroupUser(Long groupId, Long userId);
    List<CircleMember> selectByUserID(Long userId);
}
