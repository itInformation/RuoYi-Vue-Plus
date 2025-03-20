package org.dromara.circle.service;

import org.dromara.circle.domain.vo.CircleGroupCategoryVo;
import org.dromara.circle.domain.bo.CircleGroupCategoryBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 圈子-分类关系Service接口
 *
 * @author Lion Li
 * @date 2025-03-20
 */
public interface ICircleGroupCategoryService {

    /**
     * 查询圈子-分类关系
     *
     * @param groupId 主键
     * @return 圈子-分类关系
     */
    CircleGroupCategoryVo queryById(Long groupId);

    /**
     * 分页查询圈子-分类关系列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子-分类关系分页列表
     */
    TableDataInfo<CircleGroupCategoryVo> queryPageList(CircleGroupCategoryBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的圈子-分类关系列表
     *
     * @param bo 查询条件
     * @return 圈子-分类关系列表
     */
    List<CircleGroupCategoryVo> queryList(CircleGroupCategoryBo bo);

    /**
     * 新增圈子-分类关系
     *
     * @param bo 圈子-分类关系
     * @return 是否新增成功
     */
    Boolean insertByBo(CircleGroupCategoryBo bo);

    /**
     * 修改圈子-分类关系
     *
     * @param bo 圈子-分类关系
     * @return 是否修改成功
     */
    Boolean updateByBo(CircleGroupCategoryBo bo);

    /**
     * 校验并批量删除圈子-分类关系信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
