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
import org.dromara.system.domain.bo.SysVipPlanBo;
import org.dromara.system.domain.vo.SysVipPlanVo;
import org.dromara.system.domain.SysVipPlan;
import org.dromara.system.mapper.SysVipPlanMapper;
import org.dromara.system.service.ISysVipPlanService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 会员套餐Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@RequiredArgsConstructor
@Service
public class SysVipPlanServiceImpl implements ISysVipPlanService {

    private final SysVipPlanMapper baseMapper;

    /**
     * 查询会员套餐
     *
     * @param planId 主键
     * @return 会员套餐
     */
    @Override
    public SysVipPlanVo queryById(Long planId){
        return baseMapper.selectVoById(planId);
    }

    /**
     * 分页查询会员套餐列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员套餐分页列表
     */
    @Override
    public TableDataInfo<SysVipPlanVo> queryPageList(SysVipPlanBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysVipPlan> lqw = buildQueryWrapper(bo);
        Page<SysVipPlanVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员套餐列表
     *
     * @param bo 查询条件
     * @return 会员套餐列表
     */
    @Override
    public List<SysVipPlanVo> queryList(SysVipPlanBo bo) {
        LambdaQueryWrapper<SysVipPlan> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysVipPlan> buildQueryWrapper(SysVipPlanBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysVipPlan> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysVipPlan::getPlanId);
        lqw.like(StringUtils.isNotBlank(bo.getPlanName()), SysVipPlan::getPlanName, bo.getPlanName());
        lqw.eq(bo.getDuration() != null, SysVipPlan::getDuration, bo.getDuration());
        lqw.eq(bo.getOriginPrice() != null, SysVipPlan::getOriginPrice, bo.getOriginPrice());
        lqw.eq(bo.getRealPrice() != null, SysVipPlan::getRealPrice, bo.getRealPrice());
        lqw.eq(bo.getLevelRequire() != null, SysVipPlan::getLevelRequire, bo.getLevelRequire());
        lqw.eq(bo.getRenewDiscount() != null, SysVipPlan::getRenewDiscount, bo.getRenewDiscount());
        lqw.eq(StringUtils.isNotBlank(bo.getPrivileges()), SysVipPlan::getPrivileges, bo.getPrivileges());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysVipPlan::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增会员套餐
     *
     * @param bo 会员套餐
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysVipPlanBo bo) {
        SysVipPlan add = MapstructUtils.convert(bo, SysVipPlan.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPlanId(add.getPlanId());
        }
        return flag;
    }

    /**
     * 修改会员套餐
     *
     * @param bo 会员套餐
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysVipPlanBo bo) {
        SysVipPlan update = MapstructUtils.convert(bo, SysVipPlan.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysVipPlan entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除会员套餐信息
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
