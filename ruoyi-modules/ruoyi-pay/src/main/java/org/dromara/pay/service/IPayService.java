package org.dromara.pay.service;

import jakarta.servlet.http.HttpServletRequest;
import org.dromara.pay.domain.PayOrder;
import org.dromara.pay.domain.bo.PayOrderBo;

import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 17:38
 */
public interface IPayService {
    Map<String, String> createOrder(PayOrderBo order);
    void processNotify(HttpServletRequest request);

}
