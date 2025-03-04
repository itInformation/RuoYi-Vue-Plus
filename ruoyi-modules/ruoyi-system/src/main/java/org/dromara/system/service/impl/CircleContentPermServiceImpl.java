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
import org.dromara.system.domain.bo.CircleContentPermBo;
import org.dromara.system.domain.vo.CircleContentPermVo;
import org.dromara.system.domain.CircleContentPerm;
import org.dromara.system.mapper.CircleContentPermMapper;
import org.dromara.system.service.ICircleContentPermService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 内容权限关联Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class CircleContentPermServiceImpl implements ICircleContentPermService {

    private final CircleContentPermMapper baseMapper;

    /**
     * 查询内容权限关联
     *
     * @param contentId 主键
     * @return 内容权限关联
     */
    @Override
    public CircleContentPermVo queryById(Long contentId){
        return baseMapper.selectVoById(contentId);
    }

    /**
     * 分页查询内容权限关联列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 内容权限关联分页列表
     */
    @Override
    public TableDataInfo<CircleContentPermVo> queryPageList(CircleContentPermBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleContentPerm> lqw = buildQueryWrapper(bo);
        Page<CircleContentPermVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的内容权限关联列表
     *
     * @param bo 查询条件
     * @return 内容权限关联列表
     */
    @Override
    public List<CircleContentPermVo> queryList(CircleContentPermBo bo) {
        LambdaQueryWrapper<CircleContentPerm> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleContentPerm> buildQueryWrapper(CircleContentPermBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleContentPerm> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleContentPerm::getContentId);
        lqw.orderByAsc(CircleContentPerm::getUserId);
        return lqw;
    }

    /**
     * 新增内容权限关联
     *
     * @param bo 内容权限关联
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CircleContentPermBo bo) {
        CircleContentPerm add = MapstructUtils.convert(bo, CircleContentPerm.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setContentId(add.getContentId());
        }
        return flag;
    }

    /**
     * 修改内容权限关联
     *
     * @param bo 内容权限关联
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CircleContentPermBo bo) {
        CircleContentPerm update = MapstructUtils.convert(bo, CircleContentPerm.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleContentPerm entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除内容权限关联信息
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
