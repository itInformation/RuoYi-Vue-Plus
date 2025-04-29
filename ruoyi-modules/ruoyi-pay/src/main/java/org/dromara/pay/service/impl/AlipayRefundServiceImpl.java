package org.dromara.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.pay.domain.bo.PayRefundBo;
import org.dromara.pay.service.IPayRefundStrategy;
import org.dromara.pay.utils.RefundRequestNoGenerator;
import org.springframework.stereotype.Service;

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
    private final RefundRequestNoGenerator refundRequestNoGenerator;

    @Override
    public Object refund(PayRefundBo payRefundBo) {
        // 2. 构造退款请求
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(payRefundBo.getOrderNo());
        model.setRefundAmount(payRefundBo.getAmount().toString());
        model.setTradeNo(payRefundBo.getTradeNo());
        model.setRefundReason(payRefundBo.getReason());
        //根据支付宝开放平台的文档，退款请求号（out_request_no）用于标识一次退款请求，特别是在部分退款场景下必须传入。
        model.setOutRequestNo(refundRequestNoGenerator.generateRefundRequestNo());
        request.setBizModel(model);
        log.info("AlipayTradeRefundRequest request:{}",JsonUtils.toJsonString(request));
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
}
