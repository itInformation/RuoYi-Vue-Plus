package org.dromara.system.service;

import org.dromara.system.domain.vo.SysVipLevelUserVo;
import org.dromara.system.domain.bo.SysVipLevelUserBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 用户等级Service接口
 *
 * @author Lion Li
 * @date 2025-04-25
 */
public interface ISysVipLevelUserService {

    /**
     * 查询用户等级
     *
     * @param id 主键
     * @return 用户等级
     */
    SysVipLevelUserVo queryById(Long id);

    /**
     * 分页查询用户等级列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户等级分页列表
     */
    TableDataInfo<SysVipLevelUserVo> queryPageList(SysVipLevelUserBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户等级列表
     *
     * @param bo 查询条件
     * @return 用户等级列表
     */
    List<SysVipLevelUserVo> queryList(SysVipLevelUserBo bo);

    /**
     * 新增用户等级
     *
     * @param bo 用户等级
     * @return 是否新增成功
     */
    Boolean insertByBo(SysVipLevelUserBo bo);

    /**
     * 修改用户等级
     *
     * @param bo 用户等级
     * @return 是否修改成功
     */
    Boolean updateByBo(SysVipLevelUserBo bo);

    /**
     * 校验并批量删除用户等级信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
