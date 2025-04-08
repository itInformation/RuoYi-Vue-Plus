package org.dromara.system.service;

import org.dromara.system.domain.bo.ApplyMainReviewBo;
import org.dromara.system.domain.vo.ApplyMainVo;
import org.dromara.system.domain.bo.ApplyMainBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 入驻申请主Service接口
 *
 * @author Lion Li
 * @date 2025-03-24
 */
public interface IApplyMainService {

    /**
     * 查询入驻申请主
     *
     * @param applyId 主键
     * @return 入驻申请主
     */
    ApplyMainVo queryById(Long applyId);

    /**
     * 分页查询入驻申请主列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 入驻申请主分页列表
     */
    TableDataInfo<ApplyMainVo> queryPageList(ApplyMainBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的入驻申请主列表
     *
     * @param bo 查询条件
     * @return 入驻申请主列表
     */
    List<ApplyMainVo> queryList(ApplyMainBo bo);

    /**
     * 新增入驻申请主
     *
     * @param bo 入驻申请主
     * @return 是否新增成功
     */
    Boolean insertByBo(ApplyMainBo bo);

    /**
     * 修改入驻申请主
     *
     * @param bo 入驻申请主
     * @return 是否修改成功
     */
    Boolean updateByBo(ApplyMainBo bo);

    /**
     * 审核申请
     */
    Boolean reviewApplyMain(ApplyMainReviewBo bo);


    /**
     * 校验并批量删除入驻申请主信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
