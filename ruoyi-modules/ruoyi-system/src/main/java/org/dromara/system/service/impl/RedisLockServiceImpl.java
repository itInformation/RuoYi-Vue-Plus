package org.dromara.system.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.executor.RedissonLockExecutor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RedisLockServiceImpl implements IRedisLockService {
    @Autowired
    private LockTemplate lockTemplate;

    @Override
    public <T> T executeWithLock(String lockKey, int waitTime, int leaseTime, Supplier<T> supplier) {
        // 1. 尝试获取锁（单位转换为毫秒）
        log.debug("尝试获取锁：{}", lockKey);
        LockInfo lockInfo = null;
        try {
            lockInfo = lockTemplate.lock(lockKey, waitTime, leaseTime, RedissonLockExecutor.class);
            if (lockInfo == null) {
                log.warn("获取锁失败: {}", lockKey);
                throw new ServiceException("业务处理中,请稍后再试");
            }
            log.debug("成功获取锁: {}", lockKey);
            return supplier.get();
        } finally {
            if (lockInfo != null) {
                log.debug("释放锁: {}", lockKey);
                lockTemplate.releaseLock(lockInfo);
            }
        }
    }
}
