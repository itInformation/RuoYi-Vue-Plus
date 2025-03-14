package org.dromara.circle.service;

import org.dromara.circle.domain.PayOrder;

import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 16:36
 */
public interface IPayStrategy{

    Map<String, String> createOrder(PayOrder order);
    boolean verifyNotify(Map<String, String> params);
    void processNotify(Map<String, String> params);
    PayOrder queryOrder(String orderNo);
}
