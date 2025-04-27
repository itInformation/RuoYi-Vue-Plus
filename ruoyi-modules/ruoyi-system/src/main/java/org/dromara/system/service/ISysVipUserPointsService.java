package org.dromara.system.service;

import org.dromara.system.domain.vo.SysVipUserPointsVo;
import org.dromara.system.domain.bo.SysVipUserPointsBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 积分记录Service接口
 *
 * @author Lion Li
 * @date 2025-04-25
 */
public interface ISysVipUserPointsService {

    /**
     * 查询积分记录
     *
     * @param pointsId 主键
     * @return 积分记录
     */
    SysVipUserPointsVo queryById(Long pointsId);

    /**
     * 分页查询积分记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分记录分页列表
     */
    TableDataInfo<SysVipUserPointsVo> queryPageList(SysVipUserPointsBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的积分记录列表
     *
     * @param bo 查询条件
     * @return 积分记录列表
     */
    List<SysVipUserPointsVo> queryList(SysVipUserPointsBo bo);

    /**
     * 新增积分记录
     *
     * @param bo 积分记录
     * @return 是否新增成功
     */
    Boolean insertByBo(SysVipUserPointsBo bo);

    /**
     * 修改积分记录
     *
     * @param bo 积分记录
     * @return 是否修改成功
     */
    Boolean updateByBo(SysVipUserPointsBo bo);

    /**
     * 校验并批量删除积分记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
