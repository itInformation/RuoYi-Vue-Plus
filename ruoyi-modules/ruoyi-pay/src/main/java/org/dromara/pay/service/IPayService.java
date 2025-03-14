package org.dromara.pay.service;

import org.dromara.pay.domain.PayOrder;

import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 17:38
 */
public interface IPayService {
    Map<String, String> createOrder(PayOrder order);
}
