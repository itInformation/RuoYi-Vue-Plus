package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.system.domain.CreatorAsset;
import org.dromara.system.domain.vo.CreatorAssetVo;

import java.math.BigDecimal;
import java.util.List;

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
    /**
     * @param userId 用户ID
     * @return 锁定状态的资产记录
     */
    @Select("SELECT * FROM creator_asset WHERE user_id = #{userId} ")
    CreatorAsset selectByUserId(@Param("userId") Long userId);


    /**
     * 带版本号的CAS更新
     * @param userId 用户ID
     * @param oldVersion 旧版本号
     * @return 更新影响行数
     */
    @Update("UPDATE creator_asset SET " +
        "withdrawable = #{withdrawable}, " +
        "version = version + 1, " +
        "frozen_withdrawable = frozen_withdrawable  " +
        "WHERE user_id = #{userId} AND version = #{oldVersion}")
    int casFreezeWithdrawable(@Param("userId") Long userId,
                          @Param("withdrawable") BigDecimal withdrawable,
                          @Param("frozen_withdrawable") BigDecimal frozen_withdrawable,
                          @Param("oldVersion") Long oldVersion);
    /**
     * 带版本号的CAS更新充值钻石
     * @param userId 用户ID
     * @param oldVersion 旧版本号
     * @return 更新影响行数
     */
    @Update("UPDATE creator_asset SET pending_amount  = #{pendingAmount} , " +
        "frozen_pending = #{frozenPending} ," +
        "version = version + 1 " +
        "WHERE user_id = #{userId} " +
//        "AND creator_asset.pending_amount >= #{}" +
        "AND version = #{oldVersion}")
    int casFreezePendingAmount(@Param("userId") Long userId, @Param("pendingAmount") BigDecimal pendingAmount, @Param("frozenPending") BigDecimal frozenPending, @Param("oldVersion") Long oldVersion);

    @Select("SELECT user_id FROM creator_asset")
    List<Long> selectAllUserIds();
    @Update("UPDATE creator_asset SET pending_amount  = pending_amount  - #{amount} ," +
        " total_income  = total_income  + #{amount} ," +
        "version = version + 1 " +
        " WHERE user_id = #{userId} " +
        "AND version = #{oldVersion}")
    int casUpdatePendingAmount(  @Param("userId") Long userId,
                                 @Param("amount") BigDecimal amount,
                                 @Param("oldVersion") Long oldVersion);

    @Update("UPDATE creator_asset SET frozen_withdrawable   = frozen_withdrawable - #{amount} ," +
        " total_withdrawn = total_withdrawn  + #{amount} ," +
        "version = version + 1 " +
        " WHERE user_id = #{userId} " +
        "AND version = #{oldVersion}")
    int casUpdateAfterWithdraw(
        @Param("userId") Long userId,
        @Param("amount") BigDecimal amount,
        @Param("oldVersion") Long oldVersion
    );

}
