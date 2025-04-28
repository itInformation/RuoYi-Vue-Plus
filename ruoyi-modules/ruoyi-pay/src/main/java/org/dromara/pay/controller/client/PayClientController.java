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
import org.dromara.pay.domain.bo.PayOrderBo;
import org.dromara.pay.service.IPayService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    /**
     * 支付订单
     */
    @SaCheckPermission("client:order:pay")
    @Log(title = "支付订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/pay")
    public R<Map<String, String>> add(@Validated(AddGroup.class) @RequestBody PayBo bo) {
        Map<String, String> order = payService.payOrder(bo);
        return R.ok(order);
    }
    /**
     * 支付回调
     *
     */
    @PostMapping("/alipay/callback")
    @SaIgnore
    public R<Void> callBack(HttpServletRequest request) {
        log.info("alipay callback start");
        payService.processNotify(request);
        return R.ok();
    }


}
