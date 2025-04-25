package org.dromara.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.enums.DiamondOpTypeEnum;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.system.domain.UserAsset;
import org.dromara.system.domain.UserDiamondLog;
import org.dromara.system.domain.bo.UserAssetBo;
import org.dromara.system.domain.bo.UserAssetDiamondBo;
import org.dromara.system.domain.vo.UserAssetVo;
import org.dromara.system.mapper.UserAssetMapper;
import org.dromara.system.mapper.UserDiamondLogMapper;
import org.dromara.system.service.ISysUserService;
import org.dromara.system.service.IUserAssetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 普通用户资产Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@RequiredArgsConstructor
@Service
public class UserAssetServiceImpl implements IUserAssetService {

    private final UserAssetMapper baseMapper;
    private final UserDiamondLogMapper diamondLogMapper;
    private final ISysUserService userService;

    /**
     * 查询普通用户资产
     *
     * @param userId 主键
     * @return 普通用户资产
     */
    @Override
    public UserAssetVo queryById(Long userId) {
        return baseMapper.selectVoById(userId);
    }

    /**
     * 分页查询普通用户资产列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 普通用户资产分页列表
     */
    @Override
    public TableDataInfo<UserAssetVo> queryPageList(UserAssetBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<UserAsset> lqw = buildQueryWrapper(bo);
        Page<UserAssetVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的普通用户资产列表
     *
     * @param bo 查询条件
     * @return 普通用户资产列表
     */
    @Override
    public List<UserAssetVo> queryList(UserAssetBo bo) {
        LambdaQueryWrapper<UserAsset> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<UserAsset> buildQueryWrapper(UserAssetBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<UserAsset> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(UserAsset::getUserId);
        lqw.eq(bo.getDiamondBalance() != null, UserAsset::getDiamondBalance, bo.getDiamondBalance());
        lqw.eq(bo.getFrozenDiamond() != null, UserAsset::getFrozenDiamond, bo.getFrozenDiamond());
        lqw.eq(bo.getTotalConsumedDiamond() != null, UserAsset::getTotalConsumedDiamond, bo.getTotalConsumedDiamond());
        lqw.eq(bo.getTotalConsumedAmount() != null, UserAsset::getTotalConsumedAmount, bo.getTotalConsumedAmount());
        lqw.eq(bo.getTotalRefundAmount() != null, UserAsset::getTotalRefundAmount, bo.getTotalRefundAmount());
        return lqw;
    }

    /**
     * 新增普通用户资产
     *
     * @param bo 普通用户资产
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(UserAssetBo bo) {
        UserAsset add = MapstructUtils.convert(bo, UserAsset.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改普通用户资产
     *
     * @param bo 普通用户资产
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(UserAssetBo bo) {
        UserAsset update = MapstructUtils.convert(bo, UserAsset.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(UserAsset entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除普通用户资产信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rechargeDiamond(UserAssetDiamondBo bo) {
        Long diamonds = bo.getDiamond();
        Long userId = bo.getUserId();
        // 参数校验
        if (diamonds <= 0) {
            throw new ServiceException("充值钻石数必须大于0");
        }
        if (!userService.existsUser(userId)) {
            throw new ServiceException("用户不存在");
        }
        // 获取当前版本号
        UserAsset asset = baseMapper.selectForUpdate(userId);
        if (asset == null){
            // 初始化用户资产（首次充值）
            UserAsset newAsset = new UserAsset();
            newAsset.setUserId(userId);
            newAsset.setDiamondBalance(diamonds);
            baseMapper.insert(newAsset);
            asset = newAsset;
        }
        // 原子性增加钻石余额
        int updateRows = baseMapper.casUpdateBalance(userId, diamonds, asset.getVersion());
        if (updateRows == 0) {
            throw new ServiceException("操作冲突，请重试");
        }
        // 记录充值流水
        UserDiamondLog log = new UserDiamondLog();
        log.setUserId(userId);
        log.setOpType(DiamondOpTypeEnum.RECHARGE.name());
        log.setAmount(diamonds);
        diamondLogMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void consumeDiamonds(UserAssetDiamondBo bo) {
        Long diamonds = bo.getDiamond();
        Long userId = bo.getUserId();
        // 参数校验
        if (diamonds <= 0) {
            throw new ServiceException("消费钻石数必须大于0");
        }
        // 获取当前版本号
        UserAsset asset = baseMapper.selectForUpdate(userId);

        if (asset.getDiamondBalance() < diamonds) {
            throw new ServiceException("钻石余额不足");
        }
        // 使用CAS方式扣减余额
        int updateRows = baseMapper.casUpdateDiamonds(userId,(asset.getDiamondBalance() - diamonds),asset.getVersion());
        if (updateRows == 0) {
            throw new ServiceException("操作冲突，请重试");
        }

        // 记录消费流水
        UserDiamondLog log = new UserDiamondLog();
        log.setUserId(userId);
        log.setOpType(DiamondOpTypeEnum.CONSUME.name());
        log.setAmount(diamonds);
        diamondLogMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void consumeAmount(Long userId, Long amount) {
        // 参数校验
        if (amount <= 0) {
            throw new ServiceException("消费余额数必须大于0");
        }
        // 获取当前版本号
        UserAsset asset = baseMapper.selectForUpdate(userId);
        // 使用CAS方式扣减余额
        int updateRows = baseMapper.casUpdateAmount(userId,amount,asset.getVersion());
        if (updateRows == 0) {
            throw new ServiceException("操作冲突，请重试");
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void freezeDiamond(UserAssetDiamondBo bo) {
        Long diamonds = bo.getDiamond();
        Long userId = bo.getUserId();
        // 参数校验
        if (diamonds <= 0) {
            throw new ServiceException("冻结钻石数必须大于0");
        }

        // 获取当前版本号
        UserAsset asset = baseMapper.selectForUpdate(userId);

        if (asset.getDiamondBalance() < diamonds) {
            throw new ServiceException("可用钻石不足");
        }
        // 冻结操作（从可用转冻结）
        int updateRows = baseMapper.casFreezeDiamond(userId,(asset.getDiamondBalance() - diamonds),asset.getVersion());
        if (updateRows == 0) {
            throw new ServiceException("操作冲突，请重试");
        }

        // 记录冻结流水
        UserDiamondLog log = new UserDiamondLog();
        log.setUserId(userId);
        log.setOpType(DiamondOpTypeEnum.FREEZE.name());
        log.setAmount(diamonds);
        diamondLogMapper.insert(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unfreezeDiamond(UserAssetDiamondBo bo) {
        Long diamonds = bo.getDiamond();
        Long userId = bo.getUserId();
        // 参数校验
        if (diamonds <= 0) {
            throw new ServiceException("冻结钻石数必须大于0");
        }

        // 获取当前版本号
        UserAsset asset = baseMapper.selectForUpdate(userId);

        if (asset.getDiamondBalance() < diamonds) {
            throw new ServiceException("可用钻石不足");
        }
        // 解冻操作（从冻结转可用）
        int updateRows = baseMapper.casUnFreezeDiamond(userId, diamonds,asset.getVersion());
        if (updateRows == 0) {
            throw new ServiceException("操作冲突，请重试");
        }
        // 记录解冻流水
        UserDiamondLog log = new UserDiamondLog();
        log.setUserId(userId);
        log.setOpType(DiamondOpTypeEnum.UNFREEZE.name());
        log.setAmount(diamonds);
        diamondLogMapper.insert(log);
    }
}
