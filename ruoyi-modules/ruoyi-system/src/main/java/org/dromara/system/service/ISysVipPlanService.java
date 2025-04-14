package org.dromara.system.service;

import org.dromara.system.domain.vo.SysVipPlanVo;
import org.dromara.system.domain.bo.SysVipPlanBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 会员套餐Service接口
 *
 * @author Lion Li
 * @date 2025-04-14
 */
public interface ISysVipPlanService {

    /**
     * 查询会员套餐
     *
     * @param planId 主键
     * @return 会员套餐
     */
    SysVipPlanVo queryById(Long planId);

    /**
     * 分页查询会员套餐列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员套餐分页列表
     */
    TableDataInfo<SysVipPlanVo> queryPageList(SysVipPlanBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的会员套餐列表
     *
     * @param bo 查询条件
     * @return 会员套餐列表
     */
    List<SysVipPlanVo> queryList(SysVipPlanBo bo);

    /**
     * 新增会员套餐
     *
     * @param bo 会员套餐
     * @return 是否新增成功
     */
    Boolean insertByBo(SysVipPlanBo bo);

    /**
     * 修改会员套餐
     *
     * @param bo 会员套餐
     * @return 是否修改成功
     */
    Boolean updateByBo(SysVipPlanBo bo);

    /**
     * 校验并批量删除会员套餐信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
