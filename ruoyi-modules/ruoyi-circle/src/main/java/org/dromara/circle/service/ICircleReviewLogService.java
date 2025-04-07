package org.dromara.circle.service;

import org.dromara.circle.domain.vo.CircleReviewLogVo;
import org.dromara.circle.domain.bo.CircleReviewLogBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 圈子审核记录Service接口
 *
 * @author Lion Li
 * @date 2025-04-07
 */
public interface ICircleReviewLogService {

    /**
     * 查询圈子审核记录
     *
     * @param id 主键
     * @return 圈子审核记录
     */
    CircleReviewLogVo queryById(Long id);

    /**
     * 分页查询圈子审核记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子审核记录分页列表
     */
    TableDataInfo<CircleReviewLogVo> queryPageList(CircleReviewLogBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的圈子审核记录列表
     *
     * @param bo 查询条件
     * @return 圈子审核记录列表
     */
    List<CircleReviewLogVo> queryList(CircleReviewLogBo bo);

    /**
     * 新增圈子审核记录
     *
     * @param bo 圈子审核记录
     * @return 是否新增成功
     */
    Boolean insertByBo(CircleReviewLogBo bo);

    /**
     * 修改圈子审核记录
     *
     * @param bo 圈子审核记录
     * @return 是否修改成功
     */
    Boolean updateByBo(CircleReviewLogBo bo);

    /**
     * 校验并批量删除圈子审核记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

}
