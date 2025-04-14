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
import org.dromara.system.domain.bo.SysUserVipBo;
import org.dromara.system.domain.vo.SysUserVipVo;
import org.dromara.system.domain.SysUserVip;
import org.dromara.system.mapper.SysUserVipMapper;
import org.dromara.system.service.ISysUserVipService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户会员信息Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@RequiredArgsConstructor
@Service
public class SysUserVipServiceImpl implements ISysUserVipService {

    private final SysUserVipMapper baseMapper;

    /**
     * 查询用户会员信息
     *
     * @param userId 主键
     * @return 用户会员信息
     */
    @Override
    public SysUserVipVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 分页查询用户会员信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户会员信息分页列表
     */
    @Override
    public TableDataInfo<SysUserVipVo> queryPageList(SysUserVipBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysUserVip> lqw = buildQueryWrapper(bo);
        Page<SysUserVipVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户会员信息列表
     *
     * @param bo 查询条件
     * @return 用户会员信息列表
     */
    @Override
    public List<SysUserVipVo> queryList(SysUserVipBo bo) {
        LambdaQueryWrapper<SysUserVip> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysUserVip> buildQueryWrapper(SysUserVipBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysUserVip> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysUserVip::getUserId);
        lqw.eq(bo.getPlanId() != null, SysUserVip::getPlanId, bo.getPlanId());
        lqw.eq(bo.getStartTime() != null, SysUserVip::getStartTime, bo.getStartTime());
        lqw.eq(bo.getEndTime() != null, SysUserVip::getEndTime, bo.getEndTime());
        lqw.eq(bo.getCurrentLevel() != null, SysUserVip::getCurrentLevel, bo.getCurrentLevel());
        lqw.eq(bo.getTotalExp() != null, SysUserVip::getTotalExp, bo.getTotalExp());
        lqw.eq(StringUtils.isNotBlank(bo.getAutoRenew()), SysUserVip::getAutoRenew, bo.getAutoRenew());
        return lqw;
    }

    /**
     * 新增用户会员信息
     *
     * @param bo 用户会员信息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysUserVipBo bo) {
        SysUserVip add = MapstructUtils.convert(bo, SysUserVip.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改用户会员信息
     *
     * @param bo 用户会员信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysUserVipBo bo) {
        SysUserVip update = MapstructUtils.convert(bo, SysUserVip.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysUserVip entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户会员信息信息
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
