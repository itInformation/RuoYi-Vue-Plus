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
import org.dromara.system.domain.bo.SysVipLevelUserBo;
import org.dromara.system.domain.vo.SysVipLevelUserVo;
import org.dromara.system.domain.SysVipLevelUser;
import org.dromara.system.mapper.SysVipLevelUserMapper;
import org.dromara.system.service.ISysVipLevelUserService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户等级Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@RequiredArgsConstructor
@Service
public class SysVipLevelUserServiceImpl implements ISysVipLevelUserService {

    private final SysVipLevelUserMapper baseMapper;

    /**
     * 查询用户等级
     *
     * @param id 主键
     * @return 用户等级
     */
    @Override
    public SysVipLevelUserVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询用户等级列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户等级分页列表
     */
    @Override
    public TableDataInfo<SysVipLevelUserVo> queryPageList(SysVipLevelUserBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysVipLevelUser> lqw = buildQueryWrapper(bo);
        Page<SysVipLevelUserVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户等级列表
     *
     * @param bo 查询条件
     * @return 用户等级列表
     */
    @Override
    public List<SysVipLevelUserVo> queryList(SysVipLevelUserBo bo) {
        LambdaQueryWrapper<SysVipLevelUser> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysVipLevelUser> buildQueryWrapper(SysVipLevelUserBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysVipLevelUser> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysVipLevelUser::getId);
        lqw.eq(bo.getUserId() != null, SysVipLevelUser::getUserId, bo.getUserId());
        lqw.eq(bo.getTypeId() != null, SysVipLevelUser::getTypeId, bo.getTypeId());
        lqw.eq(StringUtils.isNotBlank(bo.getCurrentLevel()), SysVipLevelUser::getCurrentLevel, bo.getCurrentLevel());
        lqw.eq(bo.getExpireTime() != null, SysVipLevelUser::getExpireTime, bo.getExpireTime());
        lqw.eq(StringUtils.isNotBlank(bo.getCircleId()), SysVipLevelUser::getCircleId, bo.getCircleId());
        lqw.eq(bo.getCreatorId() != null, SysVipLevelUser::getCreatorId, bo.getCreatorId());
        lqw.eq(bo.getRevision() != null, SysVipLevelUser::getRevision, bo.getRevision());
        lqw.eq(bo.getCreatedBy() != null, SysVipLevelUser::getCreatedBy, bo.getCreatedBy());
        lqw.eq(bo.getCreatedTime() != null, SysVipLevelUser::getCreatedTime, bo.getCreatedTime());
        lqw.eq(bo.getUpdatedBy() != null, SysVipLevelUser::getUpdatedBy, bo.getUpdatedBy());
        lqw.eq(bo.getUpdatedTime() != null, SysVipLevelUser::getUpdatedTime, bo.getUpdatedTime());
        return lqw;
    }

    /**
     * 新增用户等级
     *
     * @param bo 用户等级
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysVipLevelUserBo bo) {
        SysVipLevelUser add = MapstructUtils.convert(bo, SysVipLevelUser.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户等级
     *
     * @param bo 用户等级
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysVipLevelUserBo bo) {
        SysVipLevelUser update = MapstructUtils.convert(bo, SysVipLevelUser.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysVipLevelUser entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户等级信息
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
