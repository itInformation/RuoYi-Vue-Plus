package org.dromara.pay.service;

import org.dromara.pay.domain.vo.RefundRequest;
import org.dromara.pay.domain.vo.RefundResult;

import java.util.Map;

/**
 * 支付订单Service接口
 *
 * @author Lion Li
 * @date 2025-03-10
 */
public interface IRefundService {

    /**
     * 执行退款
     * @param req 退款请求参数
     * @return 退款受理结果
     */
    RefundResult refund(RefundRequest req);

    /**
     * 处理支付平台回调
     * @param notifyData 回调数据
     * @return 处理结果
     */
    String handleNotify(Map<String, String> notifyData);
}
