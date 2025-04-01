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
import org.dromara.system.domain.bo.SysAuthRecordBo;
import org.dromara.system.domain.vo.SysAuthRecordVo;
import org.dromara.system.domain.SysAuthRecord;
import org.dromara.system.mapper.SysAuthRecordMapper;
import org.dromara.system.service.ISysAuthRecordService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-01
 */
@RequiredArgsConstructor
@Service
public class SysAuthRecordServiceImpl implements ISysAuthRecordService {

    private final SysAuthRecordMapper baseMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param recordId 主键
     * @return 【请填写功能名称】
     */
    @Override
    public SysAuthRecordVo queryById(Long recordId){
        return baseMapper.selectVoById(recordId);
    }

    /**
     * 分页查询【请填写功能名称】列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 【请填写功能名称】分页列表
     */
    @Override
    public TableDataInfo<SysAuthRecordVo> queryPageList(SysAuthRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysAuthRecord> lqw = buildQueryWrapper(bo);
        Page<SysAuthRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的【请填写功能名称】列表
     *
     * @param bo 查询条件
     * @return 【请填写功能名称】列表
     */
    @Override
    public List<SysAuthRecordVo> queryList(SysAuthRecordBo bo) {
        LambdaQueryWrapper<SysAuthRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysAuthRecord> buildQueryWrapper(SysAuthRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysAuthRecord> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysAuthRecord::getRecordId);
        lqw.eq(bo.getUserId() != null, SysAuthRecord::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getAuthType()), SysAuthRecord::getAuthType, bo.getAuthType());
        lqw.eq(StringUtils.isNotBlank(bo.getResult()), SysAuthRecord::getResult, bo.getResult());
        lqw.eq(bo.getScore() != null, SysAuthRecord::getScore, bo.getScore());
        lqw.eq(StringUtils.isNotBlank(bo.getReason()), SysAuthRecord::getReason, bo.getReason());
        lqw.eq(bo.getAuthTime() != null, SysAuthRecord::getAuthTime, bo.getAuthTime());
        return lqw;
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param bo 【请填写功能名称】
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysAuthRecordBo bo) {
        SysAuthRecord add = MapstructUtils.convert(bo, SysAuthRecord.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRecordId(add.getRecordId());
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
    public Boolean updateByBo(SysAuthRecordBo bo) {
        SysAuthRecord update = MapstructUtils.convert(bo, SysAuthRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysAuthRecord entity){
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
