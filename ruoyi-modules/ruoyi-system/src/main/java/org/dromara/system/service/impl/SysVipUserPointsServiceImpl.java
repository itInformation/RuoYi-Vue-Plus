package org.dromara.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.system.domain.SysVipLevel;
import org.dromara.system.domain.SysVipLevelUser;
import org.dromara.system.mapper.SysVipLevelMapper;
import org.dromara.system.mapper.SysVipLevelUserMapper;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.SysVipUserPointsBo;
import org.dromara.system.domain.vo.SysVipUserPointsVo;
import org.dromara.system.domain.SysVipUserPoints;
import org.dromara.system.mapper.SysVipUserPointsMapper;
import org.dromara.system.service.ISysVipUserPointsService;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
public class SysVipUserPointsServiceImpl implements ISysVipUserPointsService {

    private final SysVipUserPointsMapper baseMapper;
    private final SysVipLevelUserMapper vipLevelUserMapper;
    private final SysVipLevelMapper vipLevelMapper;

    /**
     * 查询积分记录
     *
     * @param pointsId 主键
     * @return 积分记录
     */
    @Override
    public SysVipUserPointsVo queryById(Long pointsId) {
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
    private void validEntityBeforeSave(SysVipUserPoints entity) {
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
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }


    @Transactional
    public void addPointsWithCAS(Long userId, Long typeId, Long points, Long creatorId, String circleId) {
        boolean updated = false;
        int retryCount = 0;
        final int MAX_RETRY = 3;  // 最大重试次数

        while (!updated && retryCount < MAX_RETRY) {
            // 查询当前积分和版本号
            SysVipUserPoints userPoints = baseMapper.selectByUserAndType(userId, typeId);
            if (userPoints == null) {
                // 初始化积分记录
                userPoints = new SysVipUserPoints();
                userPoints.setUserId(userId)
                    .setCurrentPoints(points)
                    .setTotalPoints(points)
                    .setVersion(0L)
                    .setCreatorId(creatorId)
                    .setCircleId(circleId);
                baseMapper.insert(userPoints);
                updated = true;
                break;
            }

            // 计算新积分和版本号
            Long newCurrent = userPoints.getCurrentPoints() + points;
            Long newTotal = userPoints.getTotalPoints() + points;

            // CAS更新（返回受影响行数）
            int affectedRows = baseMapper.updatePointsCAS(
                userId, typeId, creatorId, circleId,
                newCurrent, newTotal,
                userPoints.getVersion()
            );

            if (affectedRows > 0) {
                updated = true;
            } else {
                retryCount++;  // 版本冲突，重试
                log.warn("积分更新冲突，用户ID={}, 类型ID={}, 重试次数={}", userId, typeId, retryCount);
            }
            if (!updated) {
                throw new ServiceException("积分更新失败，请稍后重试");
            }

            // 检查等级升级
            checkLevelUp(userId, typeId, newCurrent,creatorId,circleId);
        }

    }

    private void checkLevelUp(Long userId, Long typeId, Long currentPoints, Long creatorId, String circleId) {
        // 查询匹配的等级规则（利用索引快速定位）
        SysVipLevel targetLevel = vipLevelMapper.findMatchLevel(typeId, currentPoints);

        if (targetLevel != null) {
            SysVipLevelUser sysVipLevelUser = new SysVipLevelUser();
            sysVipLevelUser.setUserId(userId)
                .setTypeId(typeId)
                .setCurrentLevel(targetLevel.getLevelName())
                .setCircleId(circleId)
                .setCreatorId(creatorId);
            // 更新用户等级（使用UPSERT语法）
            vipLevelUserMapper.upsert(sysVipLevelUser);

        }
    }
}
