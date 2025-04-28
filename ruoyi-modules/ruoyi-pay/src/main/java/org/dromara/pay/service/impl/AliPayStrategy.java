package org.dromara.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.pay.domain.vo.PayOrderVo;
import org.dromara.pay.service.IPayStrategy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 16:37
 */
@RequiredArgsConstructor
@Component("alipayStrategy")
public class AliPayStrategy implements IPayStrategy {
    private final AlipayClient client;
    @Override
    public Map<String, String> pay(PayOrderVo orderVo) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(null);
        request.setNotifyUrl(orderVo.getNotifyUrl());

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(orderVo.getOrderNo());
        model.setTotalAmount(orderVo.getAmount().toString());
        model.setSubject(orderVo.getSubject());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);
        try {
            String form = client.pageExecute(request).getBody();
            return Collections.singletonMap("form", form);
        } catch (AlipayApiException e) {
            throw new ServiceException("支付宝下单失败");
        }

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
