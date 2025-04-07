package org.dromara.circle.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.websocket.RemoteEndpoint;
import org.dromara.circle.service.ICircleCategoryService;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.dromara.circle.domain.bo.CircleGroupCategoryBo;
import org.dromara.circle.domain.vo.CircleGroupCategoryVo;
import org.dromara.circle.domain.CircleGroupCategory;
import org.dromara.circle.mapper.CircleGroupCategoryMapper;
import org.dromara.circle.service.ICircleGroupCategoryService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 圈子-分类关系Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@RequiredArgsConstructor
@Service
public class CircleGroupCategoryServiceImpl implements ICircleGroupCategoryService {

    private final CircleGroupCategoryMapper baseMapper;

    private final ICircleCategoryService circleCategoryService;

    /**
     * 查询圈子-分类关系
     *
     * @param groupId 主键
     * @return 圈子-分类关系
     */
    @Override
    public CircleGroupCategoryVo queryById(String groupId){
        return baseMapper.selectVoById(groupId);
    }

    /**
     * 分页查询圈子-分类关系列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子-分类关系分页列表
     */
    @Override
    public TableDataInfo<CircleGroupCategoryVo> queryPageList(CircleGroupCategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleGroupCategory> lqw = buildQueryWrapper(bo);
        Page<CircleGroupCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的圈子-分类关系列表
     *
     * @param bo 查询条件
     * @return 圈子-分类关系列表
     */
    @Override
    public List<CircleGroupCategoryVo> queryList(CircleGroupCategoryBo bo) {
        LambdaQueryWrapper<CircleGroupCategory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleGroupCategory> buildQueryWrapper(CircleGroupCategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleGroupCategory> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleGroupCategory::getGroupId);
        lqw.orderByAsc(CircleGroupCategory::getCatId);
        lqw.eq(bo.getDeleted() != null, CircleGroupCategory::getDeleted, bo.getDeleted());
        return lqw;
    }

    /**
     * 新增圈子-分类关系
     *
     * @param bo 圈子-分类关系
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CircleGroupCategoryBo bo) {
        CircleGroupCategory add = MapstructUtils.convert(bo, CircleGroupCategory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setGroupId(add.getGroupId());
        }
        return flag;
    }

    /**
     * 修改圈子-分类关系
     *
     * @param bo 圈子-分类关系
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CircleGroupCategoryBo bo) {
        CircleGroupCategory update = MapstructUtils.convert(bo, CircleGroupCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleGroupCategory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除圈子-分类关系信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
    @Transactional
    public void updateGroupCategories(String groupId, List<Long> catIds) {
        // 删除旧关联
        deleteByGroup(groupId);

        // 插入新关联
        catIds.forEach(catId -> {
            circleCategoryService.validateCategory(catId);
            baseMapper.insert(new CircleGroupCategory(groupId, catId));
        });

        // 更新分类统计
//        updateCategoryCount(catIds);
    }

    private void deleteByGroup(String groupId) {
        UpdateWrapper<CircleGroupCategory> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(CircleGroupCategory::getDeleted, 1);
        updateWrapper.lambda().eq(CircleGroupCategory::getGroupId, groupId);
        baseMapper.update(updateWrapper);
    }







}
