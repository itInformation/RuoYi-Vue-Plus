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
import org.dromara.system.domain.bo.CreatorWithdrawLogBo;
import org.dromara.system.domain.vo.CreatorWithdrawLogVo;
import org.dromara.system.domain.CreatorWithdrawLog;
import org.dromara.system.mapper.CreatorWithdrawLogMapper;
import org.dromara.system.service.ICreatorWithdrawLogService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 创作者提现记录Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@RequiredArgsConstructor
@Service
public class CreatorWithdrawLogServiceImpl implements ICreatorWithdrawLogService {

    private final CreatorWithdrawLogMapper baseMapper;

    /**
     * 查询创作者提现记录
     *
     * @param id 主键
     * @return 创作者提现记录
     */
    @Override
    public CreatorWithdrawLogVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询创作者提现记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 创作者提现记录分页列表
     */
    @Override
    public TableDataInfo<CreatorWithdrawLogVo> queryPageList(CreatorWithdrawLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CreatorWithdrawLog> lqw = buildQueryWrapper(bo);
        Page<CreatorWithdrawLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的创作者提现记录列表
     *
     * @param bo 查询条件
     * @return 创作者提现记录列表
     */
    @Override
    public List<CreatorWithdrawLogVo> queryList(CreatorWithdrawLogBo bo) {
        LambdaQueryWrapper<CreatorWithdrawLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CreatorWithdrawLog> buildQueryWrapper(CreatorWithdrawLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CreatorWithdrawLog> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CreatorWithdrawLog::getId);
        lqw.eq(bo.getUserId() != null, CreatorWithdrawLog::getUserId, bo.getUserId());
        lqw.eq(bo.getAmount() != null, CreatorWithdrawLog::getAmount, bo.getAmount());
        lqw.eq(bo.getStatus() != null, CreatorWithdrawLog::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增创作者提现记录
     *
     * @param bo 创作者提现记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CreatorWithdrawLogBo bo) {
        CreatorWithdrawLog add = MapstructUtils.convert(bo, CreatorWithdrawLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改创作者提现记录
     *
     * @param bo 创作者提现记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CreatorWithdrawLogBo bo) {
        CreatorWithdrawLog update = MapstructUtils.convert(bo, CreatorWithdrawLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CreatorWithdrawLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除创作者提现记录信息
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
