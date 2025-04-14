package org.dromara.system.service;

import org.dromara.system.domain.vo.SysUserVipVo;
import org.dromara.system.domain.bo.SysUserVipBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户会员信息Service接口
 *
 * @author Lion Li
 * @date 2025-04-14
 */
public interface ISysUserVipService {

    /**
     * 查询用户会员信息
     *
     * @param userId 主键
     * @return 用户会员信息
     */
    SysUserVipVo queryById(Long userId);

    /**
     * 分页查询用户会员信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户会员信息分页列表
     */
    TableDataInfo<SysUserVipVo> queryPageList(SysUserVipBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户会员信息列表
     *
     * @param bo 查询条件
     * @return 用户会员信息列表
     */
    List<SysUserVipVo> queryList(SysUserVipBo bo);

    /**
     * 新增用户会员信息
     *
     * @param bo 用户会员信息
     * @return 是否新增成功
     */
    Boolean insertByBo(SysUserVipBo bo);

    /**
     * 修改用户会员信息
     *
     * @param bo 用户会员信息
     * @return 是否修改成功
     */
    Boolean updateByBo(SysUserVipBo bo);

    /**
     * 校验并批量删除用户会员信息信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
