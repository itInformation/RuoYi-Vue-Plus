package org.dromara.circle.service;

import org.dromara.circle.domain.CircleContentReviewBo;
import org.dromara.circle.domain.bo.CircleContentBo;
import org.dromara.circle.domain.bo.CircleContentTopBo;
import org.dromara.circle.domain.vo.CircleContentVo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 圈子内容Service接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
public interface ICircleContentService {

    /**
     * 查询圈子内容
     *
     * @param contentId 主键
     * @return 圈子内容
     */
    CircleContentVo queryById(String contentId);

    /**
     * 分页查询圈子内容列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子内容分页列表
     */
    TableDataInfo<CircleContentVo> queryPageList(CircleContentBo bo, PageQuery pageQuery);
    /**
     * 分页查询圈子内容审核列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子内容分页列表
     */
    TableDataInfo<CircleContentVo> queryPageFailList(CircleContentBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的圈子内容列表
     *
     * @param bo 查询条件
     * @return 圈子内容列表
     */
    List<CircleContentVo> queryList(CircleContentBo bo);

    /**
     * 新增圈子内容
     *
     * @param bo 圈子内容
     * @return 是否新增成功
     */
    Boolean insertByBo(CircleContentBo bo);

    /**
     * 修改圈子内容
     *
     * @param bo 圈子内容
     * @return 是否修改成功
     */
    Boolean updateByBo(CircleContentBo bo);
    /**
     * 置顶圈子，现在的逻辑是一个圈子上面只置顶一个内容
     */
    Boolean topCircleContent( CircleContentTopBo bo);

    /**
     * 内容发布
     */
    Boolean publishCircleContent( CircleContentTopBo bo);

    /**
     * 审核圈子内容
     * @param bo
     * @return
     */
    Boolean reviewCircleContent(CircleContentReviewBo bo);
    /**
     * 校验并批量删除圈子内容信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 校验用户观看内容的权限
     * @param contentId
     * @param userId
     * @return
     */
    Boolean checkAccessPermission(String contentId, Long userId);
}
