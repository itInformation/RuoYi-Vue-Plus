package org.dromara.job.snailjob;

import com.aizuda.snailjob.client.job.core.annotation.JobExecutor;
import com.aizuda.snailjob.client.job.core.dto.JobArgs;
import com.aizuda.snailjob.client.model.ExecuteResult;
import com.aizuda.snailjob.common.core.util.JsonUtil;
import com.aizuda.snailjob.common.log.SnailJobLog;
import lombok.RequiredArgsConstructor;
import org.dromara.system.service.ISysCreatorStatsService;
import org.springframework.stereotype.Component;

/**
 * @author opensnail
 * @date 2024-05-17
 */
@Component
@JobExecutor(name = "systemCreatorStatsJob")
@RequiredArgsConstructor
public class SystemCreatorStatsAnnoJobExecutor {
    private final ISysCreatorStatsService sysCreatorStatsService;
    public ExecuteResult jobExecute(JobArgs jobArgs) {
        // 1. 重新计算内容总数
        // 2. 同步关注关系
        // 3. 更新缓存
        SnailJobLog.LOCAL.info("systemCreatorStatsJob. jobArgs:{}", JsonUtil.toJsonString(jobArgs));
        SnailJobLog.REMOTE.info("systemCreatorStatsJob. jobArgs:{}", JsonUtil.toJsonString(jobArgs));
        return ExecuteResult.success("创作者动态统计定时任务执行成功");
    }
}
