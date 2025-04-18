package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Update;
import org.dromara.system.domain.CreatorAsset;
import org.dromara.system.domain.vo.CreatorAssetVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.math.BigDecimal;

/**
 * 创作者资产Mapper接口
 *
 * @author Lion Li
 * @date 2025-04-18
 */
public interface CreatorAssetMapper extends BaseMapperPlus<CreatorAsset, CreatorAssetVo> {
    @Update("UPDATE creator_asset  SET withdrawable = withdrawable - #{amount},frozen_withdrawable = frozen_withdrawable  + #{amount} WHERE user_id = #{userId} AND withdrawable >= #{amount}")
    int freezeWithdrawable(Long userId, BigDecimal amount);

    @Update("UPDATE creator_asset SET pending_amount = pending_amount  - #{amount} WHERE user_id = #{userId}")
    int addPendingAmount(Long userId, BigDecimal amount);
}
