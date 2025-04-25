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
import org.dromara.system.domain.bo.SysVipUserPointsBo;
import org.dromara.system.domain.vo.SysVipUserPointsVo;
import org.dromara.system.domain.SysVipUserPoints;
import org.dromara.system.mapper.SysVipUserPointsMapper;
import org.dromara.system.service.ISysVipUserPointsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分记录Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@RequiredArgsConstructor
@Service
public class SysVipUserPointsServiceImpl implements ISysVipUserPointsService {

    private final SysVipUserPointsMapper baseMapper;

    /**
     * 查询积分记录
     *
     * @param pointsId 主键
     * @return 积分记录
     */
    @Override
    public SysVipUserPointsVo queryById(Long pointsId){
        return baseMapper.selectVoById(pointsId);
    }

    /**
     * 分页查询积分记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分记录分页列表
     */
    @Override
    public TableDataInfo<SysVipUserPointsVo> queryPageList(SysVipUserPointsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysVipUserPoints> lqw = buildQueryWrapper(bo);
        Page<SysVipUserPointsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分记录列表
     *
     * @param bo 查询条件
     * @return 积分记录列表
     */
    @Override
    public List<SysVipUserPointsVo> queryList(SysVipUserPointsBo bo) {
        LambdaQueryWrapper<SysVipUserPoints> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysVipUserPoints> buildQueryWrapper(SysVipUserPointsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysVipUserPoints> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysVipUserPoints::getPointsId);
        lqw.eq(bo.getUserId() != null, SysVipUserPoints::getUserId, bo.getUserId());
        lqw.eq(bo.getTypeId() != null, SysVipUserPoints::getTypeId, bo.getTypeId());
        lqw.eq(bo.getCurrentPoints() != null, SysVipUserPoints::getCurrentPoints, bo.getCurrentPoints());
        lqw.eq(bo.getTotalPoints() != null, SysVipUserPoints::getTotalPoints, bo.getTotalPoints());
        lqw.eq(bo.getCreatorId() != null, SysVipUserPoints::getCreatorId, bo.getCreatorId());
        lqw.eq(StringUtils.isNotBlank(bo.getCircleId()), SysVipUserPoints::getCircleId, bo.getCircleId());
        lqw.eq(bo.getRevision() != null, SysVipUserPoints::getRevision, bo.getRevision());
        lqw.eq(bo.getCreatedBy() != null, SysVipUserPoints::getCreatedBy, bo.getCreatedBy());
        lqw.eq(bo.getCreatedTime() != null, SysVipUserPoints::getCreatedTime, bo.getCreatedTime());
        lqw.eq(bo.getUpdatedBy() != null, SysVipUserPoints::getUpdatedBy, bo.getUpdatedBy());
        lqw.eq(bo.getUpdatedTime() != null, SysVipUserPoints::getUpdatedTime, bo.getUpdatedTime());
        return lqw;
    }

    /**
     * 新增积分记录
     *
     * @param bo 积分记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysVipUserPointsBo bo) {
        SysVipUserPoints add = MapstructUtils.convert(bo, SysVipUserPoints.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setPointsId(add.getPointsId());
        }
        return flag;
    }

    /**
     * 修改积分记录
     *
     * @param bo 积分记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysVipUserPointsBo bo) {
        SysVipUserPoints update = MapstructUtils.convert(bo, SysVipUserPoints.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysVipUserPoints entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除积分记录信息
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
