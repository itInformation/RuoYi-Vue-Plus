package org.dromara.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.system.domain.ApplyMain;
import org.dromara.system.domain.bo.ApplyMainBo;
import org.dromara.system.domain.bo.ApplyMainReviewBo;
import org.dromara.system.domain.vo.ApplyGuildVo;
import org.dromara.system.domain.vo.ApplyMainVo;
import org.dromara.system.domain.vo.ApplyPersonalVo;
import org.dromara.system.mapper.ApplyMainMapper;
import org.dromara.system.service.IApplyGuildService;
import org.dromara.system.service.IApplyMainService;
import org.dromara.system.service.IApplyPersonalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 入驻申请主Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@RequiredArgsConstructor
@Service
public class ApplyMainServiceImpl implements IApplyMainService {

    private final ApplyMainMapper baseMapper;
    private final IApplyPersonalService applyPersonalService;
    private final IApplyGuildService applyGuildService;

    /**
     * 查询入驻申请主
     *
     * @param applyId 主键
     * @return 入驻申请主
     */
    @Override
    public ApplyMainVo queryById(Long applyId){
        // 参数合法性校验
        if (applyId == null || applyId <= 0) {
            throw new IllegalArgumentException("非法的申请ID: " + applyId);
        }

        // 单次数据库查询
        ApplyMainVo applyMainVo = baseMapper.selectVoById(applyId);
        if (applyMainVo == null) {
            throw new ServiceException("平台申请主信息不存在, applyId:" + applyId);
        }

        // 分离校验逻辑
        ApplyPersonalVo applyPersonalVo = applyPersonalService.queryById(applyId);
        if (applyPersonalVo == null) {
            throw new ServiceException("平台申请个人资料缺失, applyId:" + applyId);
        }

        ApplyGuildVo applyGuildVo = applyGuildService.queryById(applyId);
        if (applyGuildVo == null) {
            throw new ServiceException("平台申请公会资料缺失, applyId:" + applyId);
        }

        // 直接操作已缓存对象
        applyMainVo.setApplyPersonalVo(applyPersonalVo);
        applyMainVo.setApplyGuildVo(applyGuildVo);
        return applyMainVo;
    }

    /**
     * 分页查询入驻申请主列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 入驻申请主分页列表
     */
    @Override
    public TableDataInfo<ApplyMainVo> queryPageList(ApplyMainBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ApplyMain> lqw = buildQueryWrapper(bo);
        Page<ApplyMainVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (result == null){
            return TableDataInfo.build();
        }
        buildPersonAndGuidVo(bo, result.getRecords());

        return TableDataInfo.build(result);
    }

    /**
     * apply_main apply_person apply_guild是主子表关系
     */
    private void buildPersonAndGuidVo(ApplyMainBo bo, List<ApplyMainVo> applyMainVoList) {
        if (CollectionUtils.isEmpty(applyMainVoList)){
            return;
        }
        List<ApplyPersonalVo> applyPersonalVoList = applyPersonalService.queryList(bo.getApplyPersonalBo());
        //个人申请信息
        if (CollectionUtils.isNotEmpty(applyPersonalVoList)){
            Map<Long, ApplyPersonalVo> itemsMap = applyPersonalVoList.stream()
                .collect(Collectors.toMap(ApplyPersonalVo::getApplyId, Function.identity()));
            applyMainVoList.forEach(applyMainVo -> {
                applyMainVo.setApplyPersonalVo(itemsMap.get(applyMainVo.getApplyId()));
            });
        }
        //达人申请信息
        List<ApplyGuildVo> applyGuildVoPageList = applyGuildService.queryList(bo.getApplyGuildBo());
        if (CollectionUtils.isNotEmpty(applyGuildVoPageList)){
            Map<Long, ApplyGuildVo> itemsMap = applyGuildVoPageList.stream()
                .collect(Collectors.toMap(ApplyGuildVo::getApplyId, Function.identity()));
            applyMainVoList.forEach(applyMainVo -> {
                applyMainVo.setApplyGuildVo(itemsMap.get(applyMainVo.getApplyId()));
            });
        }
    }

    /**
     * 查询符合条件的入驻申请主列表
     *
     * @param bo 查询条件
     * @return 入驻申请主列表
     */
    @Override
    public List<ApplyMainVo> queryList(ApplyMainBo bo) {
        LambdaQueryWrapper<ApplyMain> lqw = buildQueryWrapper(bo);
        List<ApplyMainVo> applyMainVoList = baseMapper.selectVoList(lqw);
        buildPersonAndGuidVo(bo,applyMainVoList);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ApplyMain> buildQueryWrapper(ApplyMainBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ApplyMain> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(ApplyMain::getApplyId);
        lqw.eq(bo.getUserId() != null, ApplyMain::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getApplyType()), ApplyMain::getApplyType, bo.getApplyType());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), ApplyMain::getStatus, bo.getStatus());
        lqw.eq(bo.getSubmitTime() != null, ApplyMain::getSubmitTime, bo.getSubmitTime());
        lqw.eq(bo.getAuditTime() != null, ApplyMain::getAuditTime, bo.getAuditTime());
        lqw.eq(StringUtils.isNotBlank(bo.getAuditComment()), ApplyMain::getAuditComment, bo.getAuditComment());
        return lqw;
    }

    /**
     * 新增入驻申请主
     *
     * @param bo 入驻申请主
     * @return 是否新增成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(ApplyMainBo bo) {
        ApplyMain add = MapstructUtils.convert(bo, ApplyMain.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setApplyId(add.getApplyId());
        }
        if (bo.getApplyPersonalBo() != null){
            applyPersonalService.insertByBo(bo.getApplyPersonalBo());
        }else {
            applyGuildService.insertByBo(bo.getApplyGuildBo());
        }
        return flag;
    }

    /**
     * 修改入驻申请主
     *
     * @param bo 入驻申请主
     * @return 是否修改成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(ApplyMainBo bo) {
        ApplyMain update = MapstructUtils.convert(bo, ApplyMain.class);
        validEntityBeforeSave(update);
        boolean flag = baseMapper.updateById(update) > 0;
        if (bo.getApplyPersonalBo() != null){
            applyPersonalService.updateByBo(bo.getApplyPersonalBo());
        }else {
            applyGuildService.updateByBo(bo.getApplyGuildBo());
        }

        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean reviewApplyMain(ApplyMainReviewBo bo) {
        ApplyMain update = new ApplyMain();
        update.setApplyId(bo.getApplyId());
        update.setStatus(bo.getStatus());
        update.setAuditTime(new Date());
        update.setAuditComment(bo.getAuditComment());
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ApplyMain entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除入驻申请主信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        applyPersonalService.deleteWithValidByIds(ids,isValid);
        applyGuildService.deleteWithValidByIds(ids,isValid);
        return baseMapper.deleteByIds(ids) > 0;
    }
}
