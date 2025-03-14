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
import org.dromara.circle.domain.bo.CircleApplyBo;
import org.dromara.circle.domain.vo.CircleApplyVo;
import org.dromara.circle.domain.CircleApply;
import org.dromara.circle.mapper.CircleApplyMapper;
import org.dromara.circle.service.ICircleApplyService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 圈子申请记录Service业务层处理
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class CircleApplyServiceImpl implements ICircleApplyService {

    private final CircleApplyMapper baseMapper;

    /**
     * 查询圈子申请记录
     *
     * @param applyId 主键
     * @return 圈子申请记录
     */
    @Override
    public CircleApplyVo queryById(Long applyId){
        return baseMapper.selectVoById(applyId);
    }

    /**
     * 分页查询圈子申请记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子申请记录分页列表
     */
    @Override
    public TableDataInfo<CircleApplyVo> queryPageList(CircleApplyBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleApply> lqw = buildQueryWrapper(bo);
        Page<CircleApplyVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的圈子申请记录列表
     *
     * @param bo 查询条件
     * @return 圈子申请记录列表
     */
    @Override
    public List<CircleApplyVo> queryList(CircleApplyBo bo) {
        LambdaQueryWrapper<CircleApply> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleApply> buildQueryWrapper(CircleApplyBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleApply> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleApply::getApplyId);
        lqw.eq(bo.getGroupId() != null, CircleApply::getGroupId, bo.getGroupId());
        lqw.eq(bo.getUserId() != null, CircleApply::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getApplyReason()), CircleApply::getApplyReason, bo.getApplyReason());
        lqw.eq(bo.getApplyStatus() != null, CircleApply::getApplyStatus, bo.getApplyStatus());
        lqw.eq(bo.getAuditUser() != null, CircleApply::getAuditUser, bo.getAuditUser());
        lqw.eq(bo.getAuditTime() != null, CircleApply::getAuditTime, bo.getAuditTime());
        return lqw;
    }

    /**
     * 新增圈子申请记录
     *
     * @param bo 圈子申请记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CircleApplyBo bo) {
        CircleApply add = MapstructUtils.convert(bo, CircleApply.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setApplyId(add.getApplyId());
        }
        return flag;
    }

    /**
     * 修改圈子申请记录
     *
     * @param bo 圈子申请记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CircleApplyBo bo) {
        CircleApply update = MapstructUtils.convert(bo, CircleApply.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleApply entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除圈子申请记录信息
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
