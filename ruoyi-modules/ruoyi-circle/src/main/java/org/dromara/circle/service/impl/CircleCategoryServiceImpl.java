package org.dromara.circle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.circle.convert.CategoryTreeConverter;
import org.dromara.circle.domain.CircleCategory;
import org.dromara.circle.domain.CircleGroupCategory;
import org.dromara.circle.domain.bo.CircleCategoryBo;
import org.dromara.circle.domain.vo.CategoryTreeVO;
import org.dromara.circle.domain.vo.CircleCategoryVo;
import org.dromara.circle.mapper.CircleCategoryMapper;
import org.dromara.circle.mapper.CircleGroupCategoryMapper;
import org.dromara.circle.service.ICircleCategoryService;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 圈子分类Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@RequiredArgsConstructor
@Service
public class CircleCategoryServiceImpl implements ICircleCategoryService {

    private final CircleCategoryMapper baseMapper;
    private final CircleGroupCategoryMapper groupCategoryMapper;

    /**
     * 查询圈子分类
     *
     * @param catId 主键
     * @return 圈子分类
     */
    @Override
    public CircleCategoryVo queryById(Long catId) {
        return baseMapper.selectVoById(catId);
    }

    /**
     * 分页查询圈子分类列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子分类分页列表
     */
    @Override
    public TableDataInfo<CircleCategoryVo> queryPageList(CircleCategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleCategory> lqw = buildQueryWrapper(bo);
        Page<CircleCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的圈子分类列表
     *
     * @param bo 查询条件
     * @return 圈子分类列表
     */
    @Override
    public List<CircleCategoryVo> queryList(CircleCategoryBo bo) {
        LambdaQueryWrapper<CircleCategory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleCategory> buildQueryWrapper(CircleCategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleCategory> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleCategory::getCatId);
        lqw.eq(bo.getParentId() != null, CircleCategory::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getCatName()), CircleCategory::getCatName, bo.getCatName());
        lqw.eq(bo.getSortOrder() != null, CircleCategory::getSortOrder, bo.getSortOrder());
        lqw.eq(bo.getLevel() != null, CircleCategory::getLevel, bo.getLevel());
        lqw.eq(bo.getIsHot() != null, CircleCategory::getIsHot, bo.getIsHot());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), CircleCategory::getIcon, bo.getIcon());
        lqw.eq(bo.getDeleted() != null, CircleCategory::getDeleted, bo.getDeleted());
        return lqw;
    }

    /**
     * 新增圈子分类
     *
     * @param bo 圈子分类
     * @return 是否新增成功
     */
    @Override
    @CacheEvict(value = "categoryTree", key = "'all'")
    public Boolean insertByBo(CircleCategoryBo bo) {
        CircleCategory add = MapstructUtils.convert(bo, CircleCategory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCatId(add.getCatId());
        }
        return flag;
    }

    /**
     * 修改圈子分类
     *
     * @param bo 圈子分类
     * @return 是否修改成功
     */
    @Override
    @CacheEvict(value = "categoryTree", key = "'all'")
    public Boolean updateByBo(CircleCategoryBo bo) {
        CircleCategory update = MapstructUtils.convert(bo, CircleCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleCategory entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除圈子分类信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    @CacheEvict(value = "categoryTree", key = "'all'")
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    /**
     * 构建圈子分类树
     */
    private List<CategoryTreeVO> buildCategoryTree(String catId) {
        List<CircleCategory> allCategories;
        // 从数据库获取所有分类
        if (StringUtils.isNotEmpty(catId)) {

            QueryWrapper<CircleCategory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cat_id", catId);
            allCategories = baseMapper.selectList(queryWrapper);
        } else {
            allCategories = baseMapper.selectList(null);
        }

        // 转换为VO并构建树形结构
        List<CategoryTreeVO> nodeList = allCategories.stream()
            .map(CategoryTreeConverter::convertFromEntity)
            .collect(Collectors.toList());

        return buildTree(0L, nodeList);
    }

    private List<CategoryTreeVO> buildTree(Long parentId, List<CategoryTreeVO> allNodes) {
        return allNodes.stream()
            .filter(node -> parentId.equals(node.getParentId()))
            .peek(node -> node.setChildren(
                buildTree(node.getId(), allNodes)
            ))
            .sorted(Comparator.comparingLong(CategoryTreeVO::getSortOrder))
            .collect(Collectors.toList());
    }

    public void validateCategory(Long catId) {
        if (existsById(catId)) {
            throw new ServiceException("无效分类ID：" + catId);
        }
    }

    public Boolean existsById(Long catId) {
        return baseMapper.selectCount(new LambdaQueryWrapper<CircleCategory>().eq(CircleCategory::getCatId, catId)) > 0;
    }

    @Cacheable(value = "categoryTree", key = "'all'")
    public List<CategoryTreeVO> getCategoryTree() {
        return buildCategoryTree(null);
    }

    @Cacheable(value = "categoryTree", key = "#catId")
    public List<CategoryTreeVO> getCategoryTree(String catId) {
        return buildCategoryTree(catId);
    }


    @Async
    public void updateCategoryCount(List<Long> catIds) {
        catIds.forEach(catId -> {
            int count = groupCategoryMapper.countGroups(catId);
            baseMapper.updateCount(catId, count);
        });
    }
}
