package org.dromara.circle.service.impl;

import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.executor.RedissonLockExecutor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.circle.domain.PayOrder;
import org.dromara.circle.domain.vo.PayOrderVo;
import org.dromara.circle.factory.PayStrategyFactory;
import org.dromara.circle.service.IPayOrderService;
import org.dromara.circle.service.IPayService;
import org.dromara.circle.service.IPayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 17:40
 */
@Slf4j
@Service
public class PayServiceImpl implements IPayService {
    @Resource
    private PayStrategyFactory strategyFactory;
    @Resource
    private IPayOrderService payOrderService;
    @Autowired
    private LockTemplate lockTemplate;
    @Override
    public Map<String, String> createOrder(PayOrder order) {
        // 1. 校验业务参数
        // 2. 调用策略生成支付订单
        IPayStrategy strategy = strategyFactory.getStrategy(order.getChannel());
        Map<String, String> order1 = strategy.createOrder(order);
        // 3. 保存订单记录（需事务管理）
//        payOrderService.insertByBo(order);
        return null;
    }

    public void validateAmount(BigDecimal requestAmount, Long orderNo) {
        PayOrderVo order = payOrderService.queryById(orderNo);

        // 使用BigDecimal的compareTo方法进行精确比较
        if (order.getAmount().compareTo(requestAmount) != 0) {
            throw new ServiceException("支付金额不一致");
        }
    }


    @Transactional
    public void processNotify(Map<String, String> params) {
        String orderNo = params.get("out_trade_no");

        // 1.使用Redis分布式锁
        String lockKey = "pay:notify:lock:" + orderNo;
        final LockInfo lockInfo = lockTemplate.lock(lockKey, 30000L, 5000L, RedissonLockExecutor.class);
        if (null == lockInfo) {
            throw new RuntimeException("业务处理中,请稍后再试");
        }
        // 获取锁成功，处理业务
        try {
            // 2.查询订单
            PayOrderVo order = payOrderService.queryById(orderNo);

            // 3.检查订单状态
            if (!"WAITING".equals(order.getStatus())) {
                log.info("订单已处理，直接返回成功");
                return;
            }

            // 4.更新订单状态
//            updateOrderStatus(orderNo, "SUCCESS");

            // 5.记录处理日志
//            insertNotifyLog(params);

        } finally {
            //释放锁
            lockTemplate.releaseLock(lockInfo);
        }
    }
}
