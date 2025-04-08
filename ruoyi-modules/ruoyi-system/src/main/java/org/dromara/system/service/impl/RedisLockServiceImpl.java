package org.dromara.system.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.executor.RedissonLockExecutor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.system.service.IRedisLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/4/8 17:47
 */
@Component
public class RedisLockServiceImpl implements IRedisLockService {
    @Autowired
    private LockTemplate lockTemplate;

    @Override
    public <T> T executeWithLock(String lockKey, int waitTime, int leaseTime, Supplier<T> supplier) {
        // 1. 尝试获取锁（单位转换为毫秒）
        LockInfo lockInfo = null;
        try {
            lockInfo = lockTemplate.lock(lockKey, waitTime, leaseTime, RedissonLockExecutor.class);
            if (lockInfo == null) {
                throw new ServiceException("业务处理中,请稍后再试");
            }
            return supplier.get();
        } finally {
            if (lockInfo != null) {
                lockTemplate.releaseLock(lockInfo);
            }
        }
    }
}
