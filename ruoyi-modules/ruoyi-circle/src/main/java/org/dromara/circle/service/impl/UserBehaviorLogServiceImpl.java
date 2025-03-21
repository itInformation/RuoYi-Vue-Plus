package org.dromara.circle.service.impl;
// ObjectMapper 的导入
import com.fasterxml.jackson.databind.ObjectMapper;

// TypeReference 的导入
import com.fasterxml.jackson.core.type.TypeReference;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.circle.domain.bo.UserBehaviorLogBo;
import org.dromara.circle.domain.vo.UserBehaviorLogVo;
import org.dromara.circle.domain.UserBehaviorLog;
import org.dromara.circle.mapper.UserBehaviorLogMapper;
import org.dromara.circle.service.IUserBehaviorLogService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户行为日志Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@RequiredArgsConstructor
@Service
public class UserBehaviorLogServiceImpl implements IUserBehaviorLogService {

    private final UserBehaviorLogMapper baseMapper;

    /**
     * 查询用户行为日志
     *
     * @param logId 主键
     * @return 用户行为日志
     */
    @Override
    public UserBehaviorLogVo queryById(Long logId){
        return baseMapper.selectVoById(logId);
    }

    /**
     * 分页查询用户行为日志列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户行为日志分页列表
     */
    @Override
    public TableDataInfo<UserBehaviorLogVo> queryPageList(UserBehaviorLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<UserBehaviorLog> lqw = buildQueryWrapper(bo);
        Page<UserBehaviorLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户行为日志列表
     *
     * @param bo 查询条件
     * @return 用户行为日志列表
     */
    @Override
    public List<UserBehaviorLogVo> queryList(UserBehaviorLogBo bo) {
        LambdaQueryWrapper<UserBehaviorLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<UserBehaviorLog> buildQueryWrapper(UserBehaviorLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<UserBehaviorLog> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(UserBehaviorLog::getLogId);
        lqw.eq(bo.getUserId() != null, UserBehaviorLog::getUserId, bo.getUserId());
        lqw.eq(bo.getGroupId() != null, UserBehaviorLog::getGroupId, bo.getGroupId());
        lqw.eq(StringUtils.isNotBlank(bo.getCatIds()), UserBehaviorLog::getCatIds, bo.getCatIds());
        lqw.eq(bo.getActionType() != null, UserBehaviorLog::getActionType, bo.getActionType());
        lqw.eq(bo.getDeleted() != null, UserBehaviorLog::getDeleted, bo.getDeleted());
        return lqw;
    }

    /**
     * 新增用户行为日志
     *
     * @param bo 用户行为日志
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(UserBehaviorLogBo bo) {
        UserBehaviorLog add = MapstructUtils.convert(bo, UserBehaviorLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setLogId(add.getLogId());
        }
        return flag;
    }

    /**
     * 修改用户行为日志
     *
     * @param bo 用户行为日志
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(UserBehaviorLogBo bo) {
        UserBehaviorLog update = MapstructUtils.convert(bo, UserBehaviorLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(UserBehaviorLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户行为日志信息
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

    public List<Long> getViewedCategories(Long userId) {
        // 1. 获取最近浏览记录
        List<UserBehaviorLog> logs = baseMapper.selectRecentViews(
            userId,
            100, // 取最近100条浏览记录
            LocalDateTime.now().minusDays(30) // 最近30天数据
        );

        // 2. 提取分类ID并统计
        Map<Long, Integer> categoryCount = new HashMap<>();

        logs.forEach(log -> {
            List<Long> cats = parseCategoryIds(log.getCatIds());
            cats.forEach(catId ->
                categoryCount.put(catId, categoryCount.getOrDefault(catId, 0) + 1)
            );
        });

        // 3. 按频率排序取Top10
        return categoryCount.entrySet().stream()
            .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
            .limit(10)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    // JSON分类ID解析方法
    private List<Long> parseCategoryIds(String json) {
        try {
            return JsonUtils.getObjectMapper().readValue(json, new TypeReference<List<Long>>(){});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }




}
