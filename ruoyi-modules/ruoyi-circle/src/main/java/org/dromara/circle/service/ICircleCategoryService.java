package org.dromara.circle.service;

import org.dromara.circle.domain.bo.CircleCategoryBo;
import org.dromara.circle.domain.vo.CategoryTreeVO;
import org.dromara.circle.domain.vo.CircleCategoryVo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 圈子分类Service接口
 *
 * @author Lion Li
 * @date 2025-03-20
 */
public interface ICircleCategoryService {

    /**
     * 查询圈子分类
     *
     * @param catId 主键
     * @return 圈子分类
     */
    CircleCategoryVo queryById(Long catId);

    /**
     * 分页查询圈子分类列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子分类分页列表
     */
    TableDataInfo<CircleCategoryVo> queryPageList(CircleCategoryBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的圈子分类列表
     *
     * @param bo 查询条件
     * @return 圈子分类列表
     */
    List<CircleCategoryVo> queryList(CircleCategoryBo bo);

    /**
     * 新增圈子分类
     *
     * @param bo 圈子分类
     * @return 是否新增成功
     */
    Boolean insertByBo(CircleCategoryBo bo);

    /**
     * 修改圈子分类
     *
     * @param bo 圈子分类
     * @return 是否修改成功
     */
    Boolean updateByBo(CircleCategoryBo bo);

    /**
     * 校验并批量删除圈子分类信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 判断分类是否存在
     * @param catId
     * @return
     */
    Boolean existsById(Long catId);

    /**
     * 校验分类是否存在
     * @param catId
     */
    void validateCategory(Long catId);

    /**
     * 查询圈子分类树
     */
    List<CategoryTreeVO> getCategoryTree(String catId);
    List<CategoryTreeVO> getCategoryTree();

}
