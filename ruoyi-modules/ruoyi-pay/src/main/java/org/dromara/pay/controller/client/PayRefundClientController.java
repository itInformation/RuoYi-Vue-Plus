package org.dromara.pay.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:33 2025/4/21
 * modified by
 */
@RestController
@RequestMapping("/pay/refund")
public class PayRefundClientController {

//    @Autowired
//    private RefundServiceFactory refundServiceFactory;

//    @PostMapping("/apply")
//    @SaCheckPermission("pay:refund:apply'")// 权限控制
//    public R<?> applyRefund(@Valid @RequestBody RefundApplyDTO dto) {
//        RefundService service = refundServiceFactory.getService(dto.getChannel());
//        RefundResult result = service.refund(convertDTO(dto));
//        return R.ok(result);
//    }

//    @PostMapping("/callback/{channel}")
//    public String callbackHandler(@PathVariable String channel,
//                                  HttpServletRequest request) {
//        Map<String, String> params = getNotifyParams(request);
//        RefundService service = refundServiceFactory.getService(channel);
//        return service.handleNotify(params);
//    }
}
