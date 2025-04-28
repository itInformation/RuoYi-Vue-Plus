package org.dromara.pay.service;

import jakarta.servlet.http.HttpServletRequest;
import org.dromara.pay.domain.bo.PayBo;
import org.dromara.pay.domain.bo.PayOrderBo;

import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 17:38
 */
public interface IPayService {
    /**
     * 支付订单
     */
    Map<String, String> payOrder(PayBo order);

    /**
     * 处理支付回调
     */
    void processNotify(HttpServletRequest request);

}
