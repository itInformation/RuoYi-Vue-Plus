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
import org.dromara.system.domain.bo.SysUserFollowBo;
import org.dromara.system.domain.vo.SysUserFollowVo;
import org.dromara.system.domain.SysUserFollow;
import org.dromara.system.mapper.SysUserFollowMapper;
import org.dromara.system.service.ISysUserFollowService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户关注关系Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-08
 */
@RequiredArgsConstructor
@Service
public class SysUserFollowServiceImpl implements ISysUserFollowService {

    private final SysUserFollowMapper baseMapper;

    /**
     * 查询用户关注关系
     *
     * @param id 主键
     * @return 用户关注关系
     */
    @Override
    public SysUserFollowVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询用户关注关系列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户关注关系分页列表
     */
    @Override
    public TableDataInfo<SysUserFollowVo> queryPageList(SysUserFollowBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysUserFollow> lqw = buildQueryWrapper(bo);
        Page<SysUserFollowVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户关注关系列表
     *
     * @param bo 查询条件
     * @return 用户关注关系列表
     */
    @Override
    public List<SysUserFollowVo> queryList(SysUserFollowBo bo) {
        LambdaQueryWrapper<SysUserFollow> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysUserFollow> buildQueryWrapper(SysUserFollowBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysUserFollow> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysUserFollow::getId);
        lqw.eq(bo.getUserId() != null, SysUserFollow::getUserId, bo.getUserId());
        lqw.eq(bo.getCreatorId() != null, SysUserFollow::getCreatorId, bo.getCreatorId());
        return lqw;
    }

    /**
     * 新增用户关注关系
     *
     * @param bo 用户关注关系
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysUserFollowBo bo) {
        SysUserFollow add = MapstructUtils.convert(bo, SysUserFollow.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户关注关系
     *
     * @param bo 用户关注关系
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysUserFollowBo bo) {
        SysUserFollow update = MapstructUtils.convert(bo, SysUserFollow.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysUserFollow entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户关注关系信息
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
