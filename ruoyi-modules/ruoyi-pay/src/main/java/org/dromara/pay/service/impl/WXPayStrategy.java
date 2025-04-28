package org.dromara.pay.service.impl;

import org.dromara.pay.domain.vo.PayOrderVo;
import org.dromara.pay.service.IPayStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 16:37
 */
@Component("wxpay")
public class WXPayStrategy implements IPayStrategy {

    @Override
    public Map<String, String> pay(PayOrderVo order) {
        return Map.of();
    }

    @Override
    public boolean verifyNotify(Map<String, String> params) {
        return false;
    }

    @Override
    public void processNotify(Map<String, String> params) {

    }

    @Override
    public PayOrderVo queryOrder(String orderNo) {
        return null;
    }
}
