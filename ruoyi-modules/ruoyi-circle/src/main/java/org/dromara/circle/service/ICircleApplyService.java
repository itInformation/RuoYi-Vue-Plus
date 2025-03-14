package org.dromara.circle.service;

import org.dromara.circle.domain.vo.CircleApplyVo;
import org.dromara.circle.domain.bo.CircleApplyBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 圈子申请记录Service接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
public interface ICircleApplyService {

    /**
     * 查询圈子申请记录
     *
     * @param applyId 主键
     * @return 圈子申请记录
     */
    CircleApplyVo queryById(Long applyId);

    /**
     * 分页查询圈子申请记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子申请记录分页列表
     */
    TableDataInfo<CircleApplyVo> queryPageList(CircleApplyBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的圈子申请记录列表
     *
     * @param bo 查询条件
     * @return 圈子申请记录列表
     */
    List<CircleApplyVo> queryList(CircleApplyBo bo);

    /**
     * 新增圈子申请记录
     *
     * @param bo 圈子申请记录
     * @return 是否新增成功
     */
    Boolean insertByBo(CircleApplyBo bo);

    /**
     * 修改圈子申请记录
     *
     * @param bo 圈子申请记录
     * @return 是否修改成功
     */
    Boolean updateByBo(CircleApplyBo bo);

    /**
     * 校验并批量删除圈子申请记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
