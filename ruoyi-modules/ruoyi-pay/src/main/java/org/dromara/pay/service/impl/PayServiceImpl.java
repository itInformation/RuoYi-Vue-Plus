package org.dromara.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.executor.RedissonLockExecutor;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.pay.domain.bo.PayBo;
import org.dromara.pay.domain.bo.PayOrderBo;
import org.dromara.pay.domain.vo.PayConfigVo;
import org.dromara.pay.domain.vo.PayOrderVo;
import org.dromara.pay.enums.PayChannelEnum;
import org.dromara.pay.enums.PayStatusEnum;
import org.dromara.pay.factory.PayStrategyFactory;
import org.dromara.pay.service.IPayConfigService;
import org.dromara.pay.service.IPayOrderService;
import org.dromara.pay.service.IPayService;
import org.dromara.pay.service.IPayStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 17:40
 */
@Slf4j
@Service
public class PayServiceImpl implements IPayService {
    @Resource
    private PayStrategyFactory strategyFactory;
    @Resource
    private IPayOrderService payOrderService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> payOrder(PayBo orderBo) {
        // 1. 校验业务参数

        PayOrderVo orderVo = validateAmount(orderBo.getAmount(), orderBo.getOrderId());

        // 2. 调用策略生成支付订单
        IPayStrategy strategy = strategyFactory.getStrategy(orderVo.getChannel());

        return strategy.pay(orderVo);
    }

    /**
     * 订单支付前校验
     */
    private PayOrderVo validateAmount(BigDecimal requestAmount, String orderId) {
        PayOrderVo order = payOrderService.queryById(orderId);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }

        // 使用BigDecimal的compareTo方法进行精确比较
        if (order.getAmount().compareTo(requestAmount) != 0) {
            throw new ServiceException("支付金额不一致");
        }
        return order;
    }



    @Override
    public void processAlipayNotify(Map<String, String> params) {

        IPayStrategy strategy = strategyFactory.getStrategy(PayChannelEnum.ALIPAY.getCode());

        strategy.processNotify(params);

    }


    @Transactional
    @Override
    public void processWxpayNotify(Map<String, String> params) {

        IPayStrategy strategy = strategyFactory.getStrategy(PayChannelEnum.WXPAY.getCode());

        strategy.processNotify(params);

    }

    private Map<String, String> convertRequestParams(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> String.join(",", entry.getValue())
            ));
    }


}
