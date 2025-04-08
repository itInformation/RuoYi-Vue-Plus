package org.dromara.system.service.impl;

import org.dromara.common.core.constant.CacheNames;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.system.domain.SysUserFollow;
import org.dromara.system.mapper.SysUserFollowMapper;
import org.dromara.system.service.IRedisLockService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.SysCreatorStatsBo;
import org.dromara.system.domain.vo.SysCreatorStatsVo;
import org.dromara.system.domain.SysCreatorStats;
import org.dromara.system.mapper.SysCreatorStatsMapper;
import org.dromara.system.service.ISysCreatorStatsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 创作者统计Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-08
 */
@RequiredArgsConstructor
@Service
public class SysCreatorStatsServiceImpl implements ISysCreatorStatsService {

    private final SysCreatorStatsMapper baseMapper;
    private final SysUserFollowMapper userFollowMapper;
    private final IRedisLockService redisLockService;

    /**
     * 查询创作者统计
     *
     * @param userId 主键
     * @return 创作者统计
     */
    @Override
    public SysCreatorStatsVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 分页查询创作者统计列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 创作者统计分页列表
     */
    @Override
    public TableDataInfo<SysCreatorStatsVo> queryPageList(SysCreatorStatsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysCreatorStats> lqw = buildQueryWrapper(bo);
        Page<SysCreatorStatsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的创作者统计列表
     *
     * @param bo 查询条件
     * @return 创作者统计列表
     */
    @Override
    public List<SysCreatorStatsVo> queryList(SysCreatorStatsBo bo) {
        LambdaQueryWrapper<SysCreatorStats> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysCreatorStats> buildQueryWrapper(SysCreatorStatsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysCreatorStats> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysCreatorStats::getUserId);
        lqw.eq(bo.getContentCount() != null, SysCreatorStats::getContentCount, bo.getContentCount());
        lqw.eq(bo.getFansCount() != null, SysCreatorStats::getFansCount, bo.getFansCount());
        lqw.eq(bo.getFollowingCount() != null, SysCreatorStats::getFollowingCount, bo.getFollowingCount());
        lqw.eq(bo.getFriendCount() != null, SysCreatorStats::getFriendCount, bo.getFriendCount());
        return lqw;
    }

    /**
     * 新增创作者统计
     *
     * @param bo 创作者统计
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysCreatorStatsBo bo) {
        SysCreatorStats add = MapstructUtils.convert(bo, SysCreatorStats.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改创作者统计
     *
     * @param bo 创作者统计
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysCreatorStatsBo bo) {
        SysCreatorStats update = MapstructUtils.convert(bo, SysCreatorStats.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysCreatorStats entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除创作者统计信息
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
    public void handleFollowAction(Long followerId, Long creatorId, boolean isFollow) {
        int delta = isFollow ? 1 : -1;

        // 更新创作者粉丝数
        baseMapper.updateFansCount(creatorId, delta);

        // 更新关注者的关注数
        baseMapper.updateFollowingCount(followerId, delta);

        // 维护关注关系
        if (isFollow) {
            SysUserFollow entity = new SysUserFollow();
            entity.setUserId(followerId);
            entity.setCreatorId(creatorId);
            userFollowMapper.insert(entity);
        } else {
            userFollowMapper.delete(new LambdaQueryWrapper<SysUserFollow>()
                .eq(SysUserFollow::getUserId, followerId)
                .eq(SysUserFollow::getCreatorId, creatorId));
        }
    }

    // 获取统计信息（带缓存）
    @Cacheable(value = CacheNames.CREATOR_STATS, key = "#userId")
    public SysCreatorStats getStats(Long userId) {
        SysCreatorStats sysCreatorStats = baseMapper.selectById(userId);
       return sysCreatorStats  != null ? sysCreatorStats :  initStats(userId);

    }
    @CacheEvict(value = CacheNames.CREATOR_STATS, key = "#userId")
    public void updateStats(Long userId) { }
    // 使用@Async处理非实时更新
    @Async
    public void asyncUpdateStats(Long userId) {
        // 批量更新逻辑...
    }


    private void updateFansCountWithLock(Long creatorId, int delta) {
        String lockKey = "creator_stats_update:" + creatorId;
        redisLockService.executeWithLock(lockKey, 3, 5, () -> {
            baseMapper.updateFansCount(creatorId, delta);

            // 处理初始化场景
            if (baseMapper.selectById(creatorId) == null) {
                initStats(creatorId);
                baseMapper.updateFansCount(creatorId, delta);
            }
            return null;
        });
    }

    private void updateFollowingCountWithLock(Long userId, int delta) {
        String lockKey = "user_following_update:" + userId;
        redisLockService.executeWithLock(lockKey, 3, 5, () -> {
            baseMapper.updateFollowingCount(userId, delta);

            if (baseMapper.selectById(userId) == null) {
                initStats(userId);
                baseMapper.updateFollowingCount(userId, delta);
            }
            return null;
        });
    }

    private SysCreatorStats initStats(Long userId) {
        return redisLockService.executeWithLock(
            "creator_stats_init:" + userId,
            3, 10,
            () -> {
                // 双重检查锁定
                SysCreatorStats existing = baseMapper.selectById(userId);
                if (existing != null) {
                    return existing;
                }

                SysCreatorStats newStats = new SysCreatorStats();
                newStats.setUserId(userId);
                newStats.setContentCount(0L);
                newStats.setFansCount(0L);
                newStats.setFollowingCount(0L);

                try {
                    baseMapper.insert(newStats);
                } catch (DuplicateKeyException e) {
                    // 处理并发插入冲突
                    return baseMapper.selectById(userId);
                }
                return newStats;
            }
        );
    }
}
