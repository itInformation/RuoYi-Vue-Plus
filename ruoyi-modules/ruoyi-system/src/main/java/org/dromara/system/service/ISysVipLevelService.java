package org.dromara.system.service;

import org.dromara.system.domain.vo.SysVipLevelVo;
import org.dromara.system.domain.bo.SysVipLevelBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 会员等级Service接口
 *
 * @author Lion Li
 * @date 2025-04-14
 */
public interface ISysVipLevelService {

    /**
     * 查询会员等级
     *
     * @param levelId 主键
     * @return 会员等级
     */
    SysVipLevelVo queryById(Long levelId);

    /**
     * 分页查询会员等级列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员等级分页列表
     */
    TableDataInfo<SysVipLevelVo> queryPageList(SysVipLevelBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会员等级列表
     *
     * @param bo 查询条件
     * @return 会员等级列表
     */
    List<SysVipLevelVo> queryList(SysVipLevelBo bo);

    /**
     * 新增会员等级
     *
     * @param bo 会员等级
     * @return 是否新增成功
     */
    Boolean insertByBo(SysVipLevelBo bo);

    /**
     * 修改会员等级
     *
     * @param bo 会员等级
     * @return 是否修改成功
     */
    Boolean updateByBo(SysVipLevelBo bo);

    /**
     * 校验并批量删除会员等级信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
