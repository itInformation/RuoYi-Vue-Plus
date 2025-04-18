package org.dromara.system.service.impl;

import org.dromara.common.core.enums.WithdrawStatusEnum;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.system.domain.CreatorIncomeLog;
import org.dromara.system.domain.CreatorWithdrawLog;
import org.dromara.system.mapper.CreatorIncomeLogMapper;
import org.dromara.system.mapper.CreatorWithdrawLogMapper;
import org.dromara.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.dromara.system.domain.bo.CreatorAssetBo;
import org.dromara.system.domain.vo.CreatorAssetVo;
import org.dromara.system.domain.CreatorAsset;
import org.dromara.system.mapper.CreatorAssetMapper;
import org.dromara.system.service.ICreatorAssetService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 创作者资产Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@RequiredArgsConstructor
@Service
public class CreatorAssetServiceImpl implements ICreatorAssetService {

    private final CreatorAssetMapper baseMapper;
    private final CreatorWithdrawLogMapper withdrawLogMapper;
    private final CreatorIncomeLogMapper incomeLogMapper;
    private final ISysUserService userService;
    /**
     * 查询创作者资产
     *
     * @param userId 主键
     * @return 创作者资产
     */
    @Override
    public CreatorAssetVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 分页查询创作者资产列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 创作者资产分页列表
     */
    @Override
    public TableDataInfo<CreatorAssetVo> queryPageList(CreatorAssetBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CreatorAsset> lqw = buildQueryWrapper(bo);
        Page<CreatorAssetVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的创作者资产列表
     *
     * @param bo 查询条件
     * @return 创作者资产列表
     */
    @Override
    public List<CreatorAssetVo> queryList(CreatorAssetBo bo) {
        LambdaQueryWrapper<CreatorAsset> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CreatorAsset> buildQueryWrapper(CreatorAssetBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CreatorAsset> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(CreatorAsset::getUserId);
        lqw.eq(bo.getWithdrawable() != null, CreatorAsset::getWithdrawable, bo.getWithdrawable());
        lqw.eq(bo.getFrozenWithdrawable() != null, CreatorAsset::getFrozenWithdrawable, bo.getFrozenWithdrawable());
        lqw.eq(bo.getPendingAmount() != null, CreatorAsset::getPendingAmount, bo.getPendingAmount());
        lqw.eq(bo.getFrozenPending() != null, CreatorAsset::getFrozenPending, bo.getFrozenPending());
        lqw.eq(bo.getTotalWithdrawn() != null, CreatorAsset::getTotalWithdrawn, bo.getTotalWithdrawn());
        lqw.eq(bo.getTotalIncome() != null, CreatorAsset::getTotalIncome, bo.getTotalIncome());
        lqw.eq(bo.getTotalRefund() != null, CreatorAsset::getTotalRefund, bo.getTotalRefund());
        return lqw;
    }

    /**
     * 新增创作者资产
     *
     * @param bo 创作者资产
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CreatorAssetBo bo) {
        CreatorAsset add = MapstructUtils.convert(bo, CreatorAsset.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改创作者资产
     *
     * @param bo 创作者资产
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CreatorAssetBo bo) {
        CreatorAsset update = MapstructUtils.convert(bo, CreatorAsset.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CreatorAsset entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除创作者资产信息
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyWithdraw(Long userId, BigDecimal amount) {
        // 参数校验
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException("提现金额必须大于0");
        }
        if (!userService.existsUser(userId)) {
            throw new ServiceException("用户不存在");
        }

        // 冻结可提现金额
        int freezeRows = baseMapper.freezeWithdrawable(userId, amount);
        if (freezeRows == 0) {
            throw new ServiceException("可提现金额不足");
        }

        // 创建提现记录（初始状态为待审核）
        CreatorWithdrawLog log = new CreatorWithdrawLog();
        log.setUserId(userId);
        log.setAmount(amount.longValue());
        log.setStatus(WithdrawStatusEnum.UN_REVIEW.getCode());
        log.setRemark("用户发起提现申请");
        withdrawLogMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void freezeWithdrawable(Long userId, BigDecimal amount) {
        // 参数校验
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException("冻结金额必须大于0");
        }

        // 执行冻结
        int updateRows = baseMapper.freezeWithdrawable(userId, amount);
        if (updateRows == 0) {
            throw new ServiceException("可提现金额不足");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addIncome(Long userId, BigDecimal amount, String sourceType) {
        // 参数校验
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException("收入金额必须大于0");
        }

        // 增加待入账金额
        int updateRows = baseMapper.addPendingAmount(userId, amount);
        if (updateRows == 0) {
            // 初始化创作者资产
            CreatorAsset newAsset = new CreatorAsset();
            newAsset.setUserId(userId);
            newAsset.setPendingAmount(amount.longValue());
            baseMapper.insert(newAsset);
        }

        // 记录收入明细
        CreatorIncomeLog log = new CreatorIncomeLog();
        log.setUserId(userId);
        log.setSourceType(sourceType);
        log.setAmount(amount.longValue());
        incomeLogMapper.insert(log);
    }

}
