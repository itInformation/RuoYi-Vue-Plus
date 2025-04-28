package org.dromara.pay.utils;

import cn.hutool.core.util.IdUtil;
import jakarta.annotation.Resource;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:10 2025/4/21
 * modified by
 */
@Component
public class OrderNoGenerator {
    // Redis key前缀（按秒限流）
    private static final String REDIS_KEY_PREFIX = "pay:order_no:";
    @Value("${machine.id:01}") // 默认值"01"
    private String machineId;
    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
        DateTimeFormatter.ofPattern("yyMMddHHmmss");

    /**
     * 生成规则：时间戳（12位） + 机器ID（2位） + 自增序列（6位）
     * 总长度：12 + 2 + 6 = 20位
     */
    public String generate() {
        // 1. 获取时间戳（线程安全方式）
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);

        // 2. 构造Redis Key
        String redisKey = REDIS_KEY_PREFIX + timestamp;

        // 3. 获取自增序列（保证原子性操作）
        Long sequence = RedisUtils.incrAtomicValue(redisKey);

        // 4. 首次创建Key时设置过期时间（60秒自动清理）
        if (sequence != null && sequence == 1L) {
            RedisUtils.expire(redisKey, 60);
        }

        // 5. 格式化输出（补足前导零）
        return String.format("%s%s%06d", timestamp, machineId, sequence);
    }
}
