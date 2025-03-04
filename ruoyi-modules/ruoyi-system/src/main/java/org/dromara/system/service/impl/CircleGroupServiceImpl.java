package org.dromara.system.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.CircleGroupBo;
import org.dromara.system.domain.vo.CircleGroupVo;
import org.dromara.system.domain.CircleGroup;
import org.dromara.system.mapper.CircleGroupMapper;
import org.dromara.system.service.ICircleGroupService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 圈子主体Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class CircleGroupServiceImpl implements ICircleGroupService {

    private final CircleGroupMapper baseMapper;

    /**
     * 查询圈子主体
     *
     * @param groupId 主键
     * @return 圈子主体
     */
    @Override
    public CircleGroupVo queryById(Long groupId){
        return baseMapper.selectVoById(groupId);
    }

    /**
     * 分页查询圈子主体列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子主体分页列表
     */
    @Override
    public TableDataInfo<CircleGroupVo> queryPageList(CircleGroupBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleGroup> lqw = buildQueryWrapper(bo);
        Page<CircleGroupVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的圈子主体列表
     *
     * @param bo 查询条件
     * @return 圈子主体列表
     */
    @Override
    public List<CircleGroupVo> queryList(CircleGroupBo bo) {
        LambdaQueryWrapper<CircleGroup> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleGroup> buildQueryWrapper(CircleGroupBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleGroup> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleGroup::getGroupId);
        lqw.like(StringUtils.isNotBlank(bo.getGroupName()), CircleGroup::getGroupName, bo.getGroupName());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), CircleGroup::getDescription, bo.getDescription());
        lqw.eq(bo.getOwnerId() != null, CircleGroup::getOwnerId, bo.getOwnerId());
        lqw.eq(StringUtils.isNotBlank(bo.getCoverImg()), CircleGroup::getCoverImg, bo.getCoverImg());
        lqw.eq(bo.getJoinMode() != null, CircleGroup::getJoinMode, bo.getJoinMode());
        return lqw;
    }

    /**
     * 新增圈子主体
     *
     * @param bo 圈子主体
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CircleGroupBo bo) {
        CircleGroup add = MapstructUtils.convert(bo, CircleGroup.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setGroupId(add.getGroupId());
        }
        return flag;
    }

    /**
     * 修改圈子主体
     *
     * @param bo 圈子主体
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CircleGroupBo bo) {
        CircleGroup update = MapstructUtils.convert(bo, CircleGroup.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleGroup entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除圈子主体信息
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
}
