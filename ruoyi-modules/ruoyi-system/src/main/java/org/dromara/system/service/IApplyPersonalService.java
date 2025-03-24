package org.dromara.system.service;

import org.dromara.system.domain.vo.ApplyPersonalVo;
import org.dromara.system.domain.bo.ApplyPersonalBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 个人入驻申请Service接口
 *
 * @author Lion Li
 * @date 2025-03-24
 */
public interface IApplyPersonalService {

    /**
     * 查询个人入驻申请
     *
     * @param applyId 主键
     * @return 个人入驻申请
     */
    ApplyPersonalVo queryById(Long applyId);

    /**
     * 分页查询个人入驻申请列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 个人入驻申请分页列表
     */
    TableDataInfo<ApplyPersonalVo> queryPageList(ApplyPersonalBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的个人入驻申请列表
     *
     * @param bo 查询条件
     * @return 个人入驻申请列表
     */
    List<ApplyPersonalVo> queryList(ApplyPersonalBo bo);

    /**
     * 新增个人入驻申请
     *
     * @param bo 个人入驻申请
     * @return 是否新增成功
     */
    Boolean insertByBo(ApplyPersonalBo bo);

    /**
     * 修改个人入驻申请
     *
     * @param bo 个人入驻申请
     * @return 是否修改成功
     */
    Boolean updateByBo(ApplyPersonalBo bo);

    /**
     * 校验并批量删除个人入驻申请信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
