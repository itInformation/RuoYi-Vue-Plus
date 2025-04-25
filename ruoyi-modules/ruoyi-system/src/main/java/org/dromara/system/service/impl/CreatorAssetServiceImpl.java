package org.dromara.system.service.impl;

import org.dromara.common.core.enums.WithdrawStatusEnum;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
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
import java.util.Date;
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

    /**
     * 提现申请（涉及可提现金额冻结）
     * 1.在提现过程中，用户发起提现请求，这时候系统需要暂时锁定这部分金额，防止用户同时进行其他操作导致资金不一致。
     * 例如，用户提现后，如果在处理期间又进行其他提现或消费，可能会造成余额不足的问题。
     *然后，withdrawable代表用户当前可以自由提现的金额，而frozen_withdrawable是冻结中的金额，表示这部分钱已经被申请提现但尚未完成处理。
     * 操作这两个字段的目的是确保在提现审核期间，这部分金额不会被重复使用或再次提现，从而保证资金的安全和一致性。
     *提现流程中的资金状态转换
     * 当用户发起提现申请时，资金需要经历以下状态变化：
     * 可提现 → 冻结中：
     * 用户发起提现后，资金需暂时从可提现状态转为冻结状态，表示这部分金额已被“预留”，不能再用于其他操作（如重复提现）。
     * 冻结中 → 已提现：
     * 提现审核通过后，冻结金额才会真正扣除，并记录到累计提现金额中。
     * 冻结中 → 可提现：
     * 若提现审核失败（如银行卡信息错误），冻结金额需退回可提现状态。
     */
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
        // 1. 查询当前资产状态
        CreatorAsset asset = baseMapper.selectByUserId(userId);

        // 2. 业务校验
        if (asset.getWithdrawable().compareTo(amount) < 0) {
            throw new ServiceException("可提现金额不足");
        }

        // 3. 计算新值
        BigDecimal newWithdrawable = asset.getWithdrawable().subtract(amount);
        BigDecimal newFrozen = asset.getFrozenWithdrawable().add(amount);
        // 冻结可提现金额
        int freezeRows = baseMapper.casFreezeWithdrawable(userId, newWithdrawable,newFrozen,asset.getVersion());
        if (freezeRows == 0) {
            throw new ServiceException("可提现金额不足");
        }

        // 创建提现记录（初始状态为待审核）
        CreatorWithdrawLog log = new CreatorWithdrawLog();
        log.setUserId(userId);
        log.setAmount(amount);
        log.setStatus(WithdrawStatusEnum.PENDING.getCode());
        log.setRemark("用户发起提现申请");
        withdrawLogMapper.insert(log);
    }

    /**
     * 将创作者的部分或全部“待入账金额”（pending_amount）暂时冻结，确保该资金在冻结期间无法被结算或使用
     * @param userId
     * @param amount
     */
    public void freezePendingAmount(Long userId, BigDecimal amount) {
        // 1. 获取当前资产
        CreatorAsset asset = baseMapper.selectByUserId(userId);

        // 2. 校验可冻结金额
        if (asset.getPendingAmount().compareTo(amount) < 0) {
            throw new ServiceException("待入账金额不足");
        }

        // 3. 计算新值
        BigDecimal newPending = asset.getPendingAmount().subtract(amount);
        BigDecimal newFrozenPending = asset.getFrozenPending().add(amount);

        // 4. CAS更新
        int updated = baseMapper.casFreezePendingAmount(
            userId,
            newPending,
            newFrozenPending,
            asset.getVersion()
        );

        if (updated == 0) {
            throw new ServiceException("金额冻结冲突，请重试");
        }
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
        // CAS更新
        int retryCount = 0;
        while (retryCount < 3) {
            CreatorAsset asset = baseMapper.selectByUserId(userId);
            BigDecimal newPending = asset.getPendingAmount().add(amount);


            int updated = baseMapper.casUpdatePendingAmount(
                userId,
                newPending,
                asset.getVersion()
            );

            if (updated > 0) {
                // 记录收入明细
                CreatorIncomeLog log = new CreatorIncomeLog();
                log.setUserId(userId);
                log.setAmount(amount);
                log.setSourceType(sourceType);
                incomeLogMapper.insert(log);
                return;
            }
            retryCount++;
        }
        throw new ServiceException("收入入账失败，请稍后重试");
    }

    @Override
    public void confirmWithdraw(Long logId,Long userId) {
        // 1. 获取提现记录
        CreatorWithdrawLog log = withdrawLogMapper.selectById(logId);

        if (log == null || !log.getUserId().equals(userId)){
            throw new ServiceException("提现记录不存在");
        }
        // 2. CAS更新提现记录状态
        int updated = withdrawLogMapper.casUpdateWithdrawStatus(
            logId,
            WithdrawStatusEnum.PENDING.getCode(),
            WithdrawStatusEnum.PAID.getCode(),
            log.getVersion()
        );

        if (updated == 0) {
            throw new ServiceException("提现状态已变更");
        }

        // 3. 更新资产（减少冻结金额，增加累计提现）
        // 3. CAS 更新资产（减少冻结金额，增加累计提现）
        CreatorAsset asset = baseMapper.selectByUserId(log.getUserId());
        int assetUpdated = baseMapper.casUpdateAfterWithdraw(
            log.getUserId(),
            log.getAmount(),
            asset.getVersion()
        );

        if (assetUpdated == 0) {
            throw new ServiceException("资产更新冲突，请人工核查");
        }
    }

    @Override
    public List<Long> selectAllUserIds() {
        return baseMapper.selectAllUserIds();
    }
}
