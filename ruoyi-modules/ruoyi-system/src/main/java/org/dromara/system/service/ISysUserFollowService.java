package org.dromara.system.service;

import org.dromara.system.domain.vo.SysUserFollowVo;
import org.dromara.system.domain.bo.SysUserFollowBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户关注关系Service接口
 *
 * @author Lion Li
 * @date 2025-04-08
 */
public interface ISysUserFollowService {

    /**
     * 查询用户关注关系
     *
     * @param id 主键
     * @return 用户关注关系
     */
    SysUserFollowVo queryById(Long id);

    /**
     * 分页查询用户关注关系列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户关注关系分页列表
     */
    TableDataInfo<SysUserFollowVo> queryPageList(SysUserFollowBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户关注关系列表
     *
     * @param bo 查询条件
     * @return 用户关注关系列表
     */
    List<SysUserFollowVo> queryList(SysUserFollowBo bo);

    /**
     * 新增用户关注关系
     *
     * @param bo 用户关注关系
     * @return 是否新增成功
     */
    Boolean insertByBo(SysUserFollowBo bo);

    /**
     * 修改用户关注关系
     *
     * @param bo 用户关注关系
     * @return 是否修改成功
     */
    Boolean updateByBo(SysUserFollowBo bo);

    /**
     * 校验并批量删除用户关注关系信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
