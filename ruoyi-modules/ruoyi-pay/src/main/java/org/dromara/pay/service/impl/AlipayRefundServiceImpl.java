package org.dromara.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.pay.domain.PayOrder;
import org.dromara.pay.domain.vo.RefundRequest;
import org.dromara.pay.domain.vo.RefundResult;
import org.dromara.pay.service.IRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:25 2025/4/21
 * modified by
 */
@Service("alipayRefundService")
public class AlipayRefundServiceImpl implements IRefundService {


    private AlipayClient alipayClient;

    @Override
    @Transactional
    public RefundResult refund(RefundRequest req) {
        // 1. 参数校验
//        PayOrder order = validateRefund(req);

        // 2. 构造退款请求
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(JSON.toJSONString(new HashMap<String, Object>(){{
//            put("out_trade_no", order.getOrderNo());
            put("refund_amount", req.getRefundAmount());
            put("out_request_no", req.getRefundNo());
        }}));

        // 3. 执行退款
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }

        // 4. 处理结果
        if (response.isSuccess()) {
            //更新订单状态
//            updateOrderStatus(order, response.getFundChange());
            return RefundResult.success(response.getTradeNo(),response.getOutTradeNo());
        } else {
            throw new ServiceException("支付宝退款失败：" + response.getSubMsg());
        }
    }

    @Override
    public String handleNotify(Map<String, String> notifyData) {
        return "";
    }

    // 其他辅助方法...
}
