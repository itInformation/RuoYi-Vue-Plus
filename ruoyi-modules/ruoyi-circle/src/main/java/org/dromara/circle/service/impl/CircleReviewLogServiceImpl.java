package org.dromara.circle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.circle.domain.CircleReviewLog;
import org.dromara.circle.domain.bo.CircleReviewLogBo;
import org.dromara.circle.domain.vo.CircleReviewLogVo;
import org.dromara.circle.mapper.CircleReviewLogMapper;
import org.dromara.circle.service.ICircleReviewLogService;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 圈子审核记录Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-07
 */
@RequiredArgsConstructor
@Service
public class CircleReviewLogServiceImpl implements ICircleReviewLogService {

    private final CircleReviewLogMapper baseMapper;

    /**
     * 查询圈子审核记录
     *
     * @param id 主键
     * @return 圈子审核记录
     */
    @Override
    public CircleReviewLogVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询圈子审核记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 圈子审核记录分页列表
     */
    @Override
    public TableDataInfo<CircleReviewLogVo> queryPageList(CircleReviewLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CircleReviewLog> lqw = buildQueryWrapper(bo);
        Page<CircleReviewLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的圈子审核记录列表
     *
     * @param bo 查询条件
     * @return 圈子审核记录列表
     */
    @Override
    public List<CircleReviewLogVo> queryList(CircleReviewLogBo bo) {
        LambdaQueryWrapper<CircleReviewLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CircleReviewLog> buildQueryWrapper(CircleReviewLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CircleReviewLog> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CircleReviewLog::getId);
        lqw.eq(bo.getCircleId() != null, CircleReviewLog::getCircleId, bo.getCircleId());
        lqw.like(StringUtils.isNotBlank(bo.getCircleName()), CircleReviewLog::getCircleName, bo.getCircleName());
        lqw.eq(bo.getUserId() != null, CircleReviewLog::getUserId, bo.getUserId());
        lqw.eq(bo.getReview() != null, CircleReviewLog::getReview, bo.getReview());
        lqw.eq(StringUtils.isNotBlank(bo.getMemo()), CircleReviewLog::getMemo, bo.getMemo());
        return lqw;
    }

    /**
     * 新增圈子审核记录
     *
     * @param bo 圈子审核记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CircleReviewLogBo bo) {
        CircleReviewLog add = MapstructUtils.convert(bo, CircleReviewLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改圈子审核记录
     *
     * @param bo 圈子审核记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CircleReviewLogBo bo) {
        CircleReviewLog update = MapstructUtils.convert(bo, CircleReviewLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CircleReviewLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除圈子审核记录信息
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
