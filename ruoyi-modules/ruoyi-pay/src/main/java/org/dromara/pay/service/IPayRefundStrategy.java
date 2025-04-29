package org.dromara.pay.service;

import org.dromara.pay.domain.bo.PayRefundBo;
import org.dromara.pay.domain.bo.RefundBo;
import org.dromara.pay.domain.vo.PayOrderVo;

import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 16:36
 */
public interface IPayRefundStrategy {

    Object refund(PayRefundBo payRefundBo);
}
