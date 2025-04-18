package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Param;
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

}
