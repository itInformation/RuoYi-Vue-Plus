package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.dromara.system.domain.UserAsset;
import org.dromara.system.domain.vo.UserAssetVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 普通用户资产Mapper接口
 *
 * @author Lion Li
 * @date 2025-04-18
 */
public interface UserAssetMapper extends BaseMapperPlus<UserAsset, UserAssetVo> {
    @Update("UPDATE user_asset SET diamond_balance = diamond_balance + #{diamonds} WHERE user_id = #{userId}")
    int updateBalance(@Param("userId") Long userId, @Param("diamonds") Long diamonds);

    @Update("UPDATE user_asset SET diamond_balance = diamond_balance - #{diamonds} WHERE user_id = #{userId} AND diamond_balance >= #{diamonds}")
    int deductBalance(@Param("userId") Long userId, @Param("diamonds") Long diamonds);

    @Update("UPDATE user_asset SET diamond_balance = diamond_balance - #{diamonds} , frozen_diamond = frozen_diamond + #{diamonds}" +
        " WHERE user_id = #{userId} AND diamond_balance >= #{diamonds}")
    int freezeDiamond(@Param("userId") Long userId, @Param("diamonds") Long diamonds);

    @Update("UPDATE user_asset SET diamond_balance = diamond_balance + #{diamonds} , frozen_diamond = frozen_diamond - #{diamonds}" +
        " WHERE user_id = #{userId} AND frozen_diamond >= #{diamonds}")
    int unFreezeDiamond(@Param("userId") Long userId, @Param("diamonds") Long diamonds);

    @Update("UPDATE user_asset SET total_consumed_amount = total_consumed_amount + #{diamonds} WHERE user_id = #{userId}")
    int updateConsumed(@Param("userId")Long userId,@Param("diamonds")  Long diamonds);


    /**
     * 悲观锁查询（带行级锁）
     * @param userId 用户ID
     * @return 锁定状态的资产记录
     */
    @Select("SELECT * FROM user_asset WHERE user_id = #{userId} FOR UPDATE")
    UserAsset selectForUpdate(@Param("userId") Long userId);

    /**
     * 带版本号的CAS更新
     * @param userId 用户ID
     * @param newBalance 新余额
     * @param oldVersion 旧版本号
     * @return 更新影响行数
     */
    @Update("UPDATE user_asset SET " +
        "diamond_balance = #{newBalance}, " +
        "version = version + 1, " +
        "total_consumed_diamond = total_consumed_diamond + #{newBalance} " +
        "WHERE user_id = #{userId} AND version = #{oldVersion}")
    int casUpdateDiamonds(@Param("userId") Long userId,
                          @Param("newBalance") Long newBalance,
                          @Param("oldVersion") Long oldVersion);
    /**
     * 带版本号的CAS更新充值钻石
     * @param userId 用户ID
     * @param diamonds 新余额
     * @param oldVersion 旧版本号
     * @return 更新影响行数
     */
    @Update("UPDATE user_asset SET diamond_balance = diamond_balance + #{diamonds}, " +
        "version = version + 1 " +
        "WHERE user_id = #{userId} AND version = #{oldVersion}")
    int casUpdateBalance(@Param("userId") Long userId, @Param("diamonds") Long diamonds, @Param("oldVersion") Long oldVersion);

    /**
     * 带版本号的CAS更新
     * @param userId 用户ID
     * @param amount 新余额
     * @param oldVersion 旧版本号
     * @return 更新影响行数
     */
    @Update("UPDATE user_asset SET " +
        "version = version + 1, " +
        "total_consumed_amount = total_consumed_amount + #{newBalance} " +
        "WHERE user_id = #{userId} AND version = #{oldVersion}")
    int casUpdateAmount(@Param("userId") Long userId,
                  @Param("newBalance") Long amount,
                  @Param("oldVersion") Long oldVersion);

    /**
     * 带版本号的CAS更新冻结钻石

     */
    @Update("UPDATE user_asset SET diamond_balance = diamond_balance - #{diamonds} ," +
        " frozen_diamond = frozen_diamond + #{diamonds} ," +
        "version = version + 1 " +
        " WHERE user_id = #{userId} AND diamond_balance >= #{diamonds} " +
        "AND version = #{oldVersion}")
    int casFreezeDiamond(@Param("userId") Long userId, @Param("diamonds") Long diamonds,@Param("oldVersion") Long oldVersion);
    /**
     * 带版本号的CAS更新冻结钻石
     */
    @Update("UPDATE user_asset SET diamond_balance = diamond_balance + #{diamonds} , version = version + 1 , frozen_diamond = frozen_diamond - #{diamonds}" +
        " WHERE user_id = #{userId} AND frozen_diamond >= #{diamonds} AND version = #{oldVersion}")
    int casUnFreezeDiamond(@Param("userId") Long userId, @Param("diamonds") Long diamonds,@Param("oldVersion") Long oldVersion);
}
