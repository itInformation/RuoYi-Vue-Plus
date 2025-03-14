package org.dromara.circle.service;

import org.dromara.circle.domain.PayOrder;

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
