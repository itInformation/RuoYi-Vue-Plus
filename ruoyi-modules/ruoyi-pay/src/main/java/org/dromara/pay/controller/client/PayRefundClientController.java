package org.dromara.pay.controller.client;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.dromara.common.core.domain.R;
import org.dromara.pay.domain.bo.RefundBo;
import org.dromara.pay.service.IPayRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退款 app端
 *
 * @author minghuiZhang
 * @date created in 下午10:33 2025/4/21
 * modified by
 */
@RestController
@RequestMapping("/client/pay/refund")
public class PayRefundClientController {

    @Autowired
    private IPayRefundService refundService;

    /**
     * 退款申请
     * @param bo
     * @return
     */
    @PostMapping("/apply")
    @SaCheckPermission("pay:refund:apply")// 权限控制
    public R<?> applyRefund(@Valid @RequestBody RefundBo bo) {
        String refund = refundService.refund(bo);
        return R.ok(refund);
    }

    @PostMapping("/callback/")
    @SaIgnore
    public R<?> callbackHandler(HttpServletRequest request) {
        refundService.processNotify(request);
        return R.ok();
    }
}
