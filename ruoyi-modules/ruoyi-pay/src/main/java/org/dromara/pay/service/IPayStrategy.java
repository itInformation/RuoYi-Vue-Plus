package org.dromara.pay.service;

import org.dromara.pay.domain.vo.PayOrderVo;

import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 16:36
 */
public interface IPayStrategy{

    Map<String, String> pay(PayOrderVo order);
    boolean verifyNotify(Map<String, String> params);
    void processNotify(Map<String, String> params);
    PayOrderVo queryOrder(String orderNo);
}
