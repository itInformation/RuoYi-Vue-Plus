package org.dromara.pay.utils;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:10 2025/4/21
 * modified by
 */
public class OrderNoGenerator {
    // 基础配置（建议通过配置中心管理）
    private static final String BUSINESS_PREFIX = "ORD"; // 业务标识（3位）
    private static final int DATA_CENTER_ID = 1;         // 数据中心ID（0-31）
    private static final int WORKER_ID = 1;              // 工作节点ID（0-31）

    // 时间相关配置
    private static final long EPOCH = Instant.parse("2024-01-01T00:00:00Z").toEpochMilli();

    // 序列号相关
    private static final int SEQUENCE_BITS = 12;
    private static final AtomicInteger sequence = new AtomicInteger(0);
    private static final int MAX_SEQUENCE = (1 << SEQUENCE_BITS) - 1;

    // ID组成
    private static final int TIMESTAMP_SHIFT = SEQUENCE_BITS + 10; // 12+5+5
    private static final int DATA_CENTER_SHIFT = SEQUENCE_BITS + 5;
    private static final int WORKER_SHIFT = SEQUENCE_BITS;

    // 异常处理
    private static long lastTimestamp = -1L;

    public static synchronized String generate() {
        long currentTimestamp = timeGen();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards");
        }

        if (currentTimestamp == lastTimestamp) {
            int seq = sequence.incrementAndGet() & MAX_SEQUENCE;
            if (seq == 0) {
                currentTimestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence.set(0);
        }

        lastTimestamp = currentTimestamp;

        long id = ((currentTimestamp - EPOCH) << TIMESTAMP_SHIFT)
            | (DATA_CENTER_ID << DATA_CENTER_SHIFT)
            | (WORKER_ID << WORKER_SHIFT)
            | sequence.get();

        return BUSINESS_PREFIX + formatTimestamp(currentTimestamp) + id;
    }

    public static synchronized String generate(String businessType) {
        long currentTimestamp = timeGen();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards");
        }

        if (currentTimestamp == lastTimestamp) {
            int seq = sequence.incrementAndGet() & MAX_SEQUENCE;
            if (seq == 0) {
                currentTimestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence.set(0);
        }

        lastTimestamp = currentTimestamp;

        long id = ((currentTimestamp - EPOCH) << TIMESTAMP_SHIFT)
            | (DATA_CENTER_ID << DATA_CENTER_SHIFT)
            | (WORKER_ID << WORKER_SHIFT)
            | sequence.get();

        return businessType + formatTimestamp(currentTimestamp) + id;
    }

    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }

    private static String formatTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return instant.toString()
            .replaceAll("[-T:Z]","")
                .substring(2, 15); // 示例输出：240102153015（年月日时分秒）
    }
}
