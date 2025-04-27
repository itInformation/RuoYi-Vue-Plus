package org.dromara.job.snailjob;

import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import com.aizuda.snailjob.common.log.SnailJobLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.system.domain.CreatorAsset;
import org.dromara.system.mapper.CreatorAssetMapper;
import org.dromara.system.mapper.CreatorIncomeLogMapper;
import org.dromara.system.mapper.CreatorWithdrawLogMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author opensnail
 * @date 2024-05-17
 */
@Component
@JobExecutor(name = "systemCreatorStatsJob")
@RequiredArgsConstructor
@Slf4j
public class CreatorDailyReconciliationAnnoJobExecutor {
    private final CreatorAssetMapper creatorAssetMapper;
    private final CreatorIncomeLogMapper incomeLogMapper;
    private final CreatorWithdrawLogMapper withdrawLogMapper;
    public ExecuteResult dailyReconciliation(JobArgs jobArgs) {
        // 1. 重新计算内容总数
        // 2. 同步关注关系
        // 3. 更新缓存
        SnailJobLog.LOCAL.info("dailyReconciliation. jobArgs:{}", JsonUtil.toJsonString(jobArgs));

// 1. 检查资产总额是否等于各流水总和
        List<Long> userIds = creatorAssetMapper.selectAllUserIds();

        userIds.forEach(userId -> {
            // 理论值计算
            BigDecimal theoreticalTotal = incomeLogMapper.sumIncome(userId)
                .subtract(withdrawLogMapper.sumWithdrawn(userId));
//                .subtract(refundLogMapper.sumRefund(userId));

            // 实际值查询
            CreatorAsset asset = creatorAssetMapper.selectByUserId(userId);
            BigDecimal actualTotal = asset.getWithdrawable()
                .add(asset.getFrozenWithdrawable())
                .add(asset.getPendingAmount())
                .add(asset.getFrozenPending());

            // 误差检查
            if (theoreticalTotal.compareTo(actualTotal) != 0) {
              log.error("创作者资产对账异常，用户ID：" + userId);
            }
        });

        SnailJobLog.REMOTE.info("dailyReconciliation. jobArgs:{}", JsonUtil.toJsonString(jobArgs));
        return ExecuteResult.success("创作者动态统计定时任务执行成功");
    }
}
