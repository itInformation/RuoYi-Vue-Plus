package org.dromara.pay.controller.client;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.web.core.BaseController;
import org.dromara.pay.domain.bo.PayBo;
import org.dromara.pay.service.IPayRefundService;
import org.dromara.pay.service.IPayService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 支付订单 app端
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/client/pay")
public class PayClientController extends BaseController {

    private final IPayService payService;
    private final IPayRefundService payRefundService;
    /**
     * 支付订单
     */
    @SaCheckPermission("client:order:pay")
    @Log(title = "支付订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/payOrder")
    public R<Map<String, String>> payOrder(@Validated(AddGroup.class) @RequestBody PayBo bo) {
        Map<String, String> order = payService.payOrder(bo);
        return R.ok(order);
    }
    /**
     * 支付宝支付回调
     *
     */
    @PostMapping("/alipay/callback")
    @SaIgnore
    public R<Void> alipayCallBack(HttpServletRequest request) {
        log.info("alipay callback start");
        Map<String, String> params = convertRequestParams(request);
        //退款回调不处理
        if (!verifyBasicParams(params)){
            //支付回调
            payService.processAlipayNotify(params);
        }
        return R.ok();
    }

    /**
     * 微信支付回调
     *
     */
    @PostMapping("/wxpay/callback")
    @SaIgnore
    public R<Void> wXCallBack(HttpServletRequest request) {
        log.info("wxPay callback start");
        Map<String, String> params = convertRequestParams(request);
        //退款回调
        if (!verifyBasicParams(params)){
            payService.processWxpayNotify(params);
        }
        return R.ok();
    }

    private Map<String, String> convertRequestParams(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> String.join(",", entry.getValue())
            ));
    }

    private boolean verifyBasicParams(Map<String, String> params) {
        return params.containsKey("gmt_refund")
            && params.containsKey("refund_fee");
    }

}
