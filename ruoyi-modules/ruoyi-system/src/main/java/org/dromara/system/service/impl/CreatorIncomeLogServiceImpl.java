package org.dromara.system.service.impl;

import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.CreatorIncomeLogBo;
import org.dromara.system.domain.vo.CreatorIncomeLogVo;
import org.dromara.system.domain.CreatorIncomeLog;
import org.dromara.system.mapper.CreatorIncomeLogMapper;
import org.dromara.system.service.ICreatorIncomeLogService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 创作者收入明细Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@RequiredArgsConstructor
@Service
public class CreatorIncomeLogServiceImpl implements ICreatorIncomeLogService {

    private final CreatorIncomeLogMapper baseMapper;

    /**
     * 查询创作者收入明细
     *
     * @param id 主键
     * @return 创作者收入明细
     */
    @Override
    public CreatorIncomeLogVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询创作者收入明细列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 创作者收入明细分页列表
     */
    @Override
    public TableDataInfo<CreatorIncomeLogVo> queryPageList(CreatorIncomeLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CreatorIncomeLog> lqw = buildQueryWrapper(bo);
        Page<CreatorIncomeLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }
    public TableDataInfo<CreatorIncomeLogVo> queryClientPageList(CreatorIncomeLogBo bo, PageQuery pageQuery) {
        Long loginUserId = LoginHelper.getUserId();
        if (!bo.getUserId().equals(loginUserId)) {
            throw new ServiceException("无权查看他人收益记录");
        }
        LambdaQueryWrapper<CreatorIncomeLog> lqw = buildQueryWrapper(bo);
        Page<CreatorIncomeLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的创作者收入明细列表
     *
     * @param bo 查询条件
     * @return 创作者收入明细列表
     */
    @Override
    public List<CreatorIncomeLogVo> queryList(CreatorIncomeLogBo bo) {
        LambdaQueryWrapper<CreatorIncomeLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CreatorIncomeLog> buildQueryWrapper(CreatorIncomeLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CreatorIncomeLog> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CreatorIncomeLog::getId);
        lqw.eq(bo.getUserId() != null, CreatorIncomeLog::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getSourceType()), CreatorIncomeLog::getSourceType, bo.getSourceType());
        lqw.eq(bo.getAmount() != null, CreatorIncomeLog::getAmount, bo.getAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderNo()), CreatorIncomeLog::getOrderNo, bo.getOrderNo());
        return lqw;
    }

    /**
     * 新增创作者收入明细
     *
     * @param bo 创作者收入明细
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CreatorIncomeLogBo bo) {
        CreatorIncomeLog add = MapstructUtils.convert(bo, CreatorIncomeLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改创作者收入明细
     *
     * @param bo 创作者收入明细
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CreatorIncomeLogBo bo) {
        CreatorIncomeLog update = MapstructUtils.convert(bo, CreatorIncomeLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CreatorIncomeLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除创作者收入明细信息
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
