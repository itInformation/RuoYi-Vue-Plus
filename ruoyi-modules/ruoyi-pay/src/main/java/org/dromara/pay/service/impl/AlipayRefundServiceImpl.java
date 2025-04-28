package org.dromara.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.pay.domain.PayOrder;
import org.dromara.pay.domain.bo.PayRefundBo;
import org.dromara.pay.domain.bo.RefundBo;
import org.dromara.pay.domain.vo.PayConfigVo;
import org.dromara.pay.domain.vo.RefundRequest;
import org.dromara.pay.domain.vo.RefundResult;
import org.dromara.pay.service.IPayRefundStrategy;
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
@Slf4j
@Service("alipayRefundService")
@RequiredArgsConstructor
public class AlipayRefundServiceImpl implements IPayRefundStrategy {
    private final AlipayClient alipayClient;
    @Override
    public Object refund(PayRefundBo payRefundBo) {
        // 2. 构造退款请求
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(JSON.toJSONString(new HashMap<String, Object>(){{
            put("out_trade_no", payRefundBo.getOrderNo());
            put("refund_amount", payRefundBo.getAmount());
            put("out_request_no", payRefundBo.getRefundNo());
        }}));

        // 3. 执行退款
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
            if (!response.isSuccess()) {
                throw new ServiceException("支付宝退款失败：" + response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            log.error("支付宝退款接口调用异常", e);
            throw new ServiceException("退款申请失败");
        }
        return response;
    }

    @Override
    public boolean verifyNotify(Map<String, String> params) {
        return false;
    }

    // 其他辅助方法...
}
