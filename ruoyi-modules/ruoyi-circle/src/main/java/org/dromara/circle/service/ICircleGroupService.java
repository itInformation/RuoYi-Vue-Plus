package org.dromara.circle.service;

import org.dromara.circle.domain.vo.CircleGroupVo;
import org.dromara.circle.domain.bo.CircleGroupBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 圈子主体Service接口
 *
 * @author Lion Li
 * @date 2025-03-03
 */
public interface ICircleGroupService {

    /**
     * 查询圈子主体
     *
     * @param groupId 主键
     * @return 圈子主体
     */
    CircleGroupVo queryById(Long groupId);

    /**
     * 按照userId查询圈子主体
     * @param userId
     * @return
     */
    List<CircleGroupVo> queryByUserId(Long userId);
    /**
     * 查询回收站内的圈子主体
     * @param groupId
     * @return
     */
    CircleGroupVo queryByIdWithRecycleBin(Long groupId);

    /**
     * 分页查询圈子主体列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子主体分页列表
     */
    TableDataInfo<CircleGroupVo> queryPageList(CircleGroupBo bo, PageQuery pageQuery);
    /**
     * 查询普通用户可见的圈子 app端普通用户使用
     */
    TableDataInfo<CircleGroupVo> queryPageListWithClient(CircleGroupBo bo, PageQuery pageQuery);
    /**
     * 查询用户可见的圈子 app端达人用户使用
     */
    TableDataInfo<CircleGroupVo> queryOwnerPageListWithClient(CircleGroupBo bo, PageQuery pageQuery);
    /**
     * 查询用户审核失败的圈子 app端达人用户使用
     */
    TableDataInfo<CircleGroupVo> queryOwnerReviewFailurePageListWithClient(CircleGroupBo bo, PageQuery pageQuery)
    /**
     * 分页查询需要审核的圈子 管理端使用
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子主体分页列表
     */
    TableDataInfo<CircleGroupVo> queryReviewPageList(CircleGroupBo bo, PageQuery pageQuery);
    /**
     * 回收站圈子主体列表 管理端使用
     */
    TableDataInfo<CircleGroupVo> queryPageListWithRecycleBin(CircleGroupBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的圈子主体列表
     *
     */
    List<CircleGroupVo> queryList(CircleGroupBo bo);
    /**
     * 新增圈子主体
     *
     */
    Boolean insertByBo(CircleGroupBo bo);

    /**
     * 修改圈子主体
     *
     * @param bo 圈子主体
     * @return 是否修改成功
     */
    Boolean updateByBo(CircleGroupBo bo);

    /**
     * 校验并批量删除圈子主体信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
    /**
     * 校验并批量删除圈子主体信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteRecycleBinByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 删除用户圈子主体信息
     * @param id
     * @param isValid
     * @return
     */
    Boolean deleteWithValidById(Long id, Boolean isValid);

    /**
     * 从回收站删除
     * @param id
     * @param isValid
     * @return
     */
    Boolean deleteRecycleBinById(Long id, Boolean isValid);
}
