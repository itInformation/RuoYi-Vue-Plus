package org.dromara.system.service;

import java.util.function.Supplier;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/4/8 17:47
 */
public interface IRedisLockService {
    <T> T executeWithLock(String lockKey, int waitTime, int leaseTime, Supplier<T> supplier);
}
