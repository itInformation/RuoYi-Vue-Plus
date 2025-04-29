package org.dromara.pay.service.impl;

import org.dromara.pay.domain.bo.PayRefundBo;
import org.dromara.pay.domain.vo.RefundResult;
import org.dromara.pay.service.IPayRefundStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:25 2025/4/21
 * modified by
 */
@Service("wxpayRefundService")
public class WxpayRefundServiceImpl implements IPayRefundStrategy {

//    @Autowired
//    private WxPayService wxPayService;

    @Override
    @Transactional
    public RefundResult refund(PayRefundBo payRefundBo) {

////
////        // 2. 构造退款请求
//        WxPayRefundRequest request = new WxPayRefundRequest();
//        request.setOutTradeNo(order.getOrderNo());
//        request.setOutRefundNo(req.getRefundNo());
//        request.setTotalFee(order.getTotalAmount().multiply(100).intValue());
//        request.setRefundFee(req.getRefundAmount().multiply(100).intValue());
//
//        // 3. 执行退款
//        WxPayRefundResult result = wxPayService.refund(request);
//
//        // 4. 处理结果
//        if (result.getReturnCode().equals("SUCCESS")) {
//            updateOrderStatus(order, result.getResultCode());
//            return RefundResult.success(result.getRefundId());
//        } else {
//            throw new ServiceException("微信退款失败：" + result.getReturnMsg());
//        }
        return null;
    }



}
