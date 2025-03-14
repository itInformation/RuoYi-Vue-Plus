package org.dromara.circle.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.circle.domain.bo.CircleAuditLogBo;
import org.dromara.circle.domain.vo.CircleAuditLogVo;
import org.dromara.circle.domain.CircleAuditLog;
import org.dromara.circle.mapper.CircleAuditLogMapper;
import org.dromara.circle.service.ICircleAuditLogService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class CircleAuditLogServiceImpl implements ICircleAuditLogService {

    private final CircleAuditLogMapper baseMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param logId 主键
     * @return 【请填写功能名称】
     */
    @Override
    public CircleAuditLogVo queryById(Long logId){
        return baseMapper.selectVoById(logId);
    }

    /**
     * 分页查询【请填写功能名称】列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 【请填写功能名称】分页列表
     */
    @Override
    public TableDataInfo<CircleAuditLogVo> queryPageList(CircleAuditLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleAuditLog> lqw = buildQueryWrapper(bo);
        Page<CircleAuditLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的【请填写功能名称】列表
     *
     * @param bo 查询条件
     * @return 【请填写功能名称】列表
     */
    @Override
    public List<CircleAuditLogVo> queryList(CircleAuditLogBo bo) {
        LambdaQueryWrapper<CircleAuditLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleAuditLog> buildQueryWrapper(CircleAuditLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleAuditLog> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleAuditLog::getLogId);
        lqw.eq(bo.getGroupId() != null, CircleAuditLog::getGroupId, bo.getGroupId());
        lqw.eq(bo.getUserId() != null, CircleAuditLog::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getActionType()), CircleAuditLog::getActionType, bo.getActionType());
        lqw.eq(bo.getTargetId() != null, CircleAuditLog::getTargetId, bo.getTargetId());
        lqw.eq(StringUtils.isNotBlank(bo.getDetail()), CircleAuditLog::getDetail, bo.getDetail());
        lqw.eq(StringUtils.isNotBlank(bo.getIpAddress()), CircleAuditLog::getIpAddress, bo.getIpAddress());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param bo 【请填写功能名称】
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CircleAuditLogBo bo) {
        CircleAuditLog add = MapstructUtils.convert(bo, CircleAuditLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setLogId(add.getLogId());
        }
        return flag;
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param bo 【请填写功能名称】
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CircleAuditLogBo bo) {
        CircleAuditLog update = MapstructUtils.convert(bo, CircleAuditLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleAuditLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除【请填写功能名称】信息
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
