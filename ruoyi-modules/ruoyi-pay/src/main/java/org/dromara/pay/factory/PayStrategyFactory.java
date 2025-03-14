package org.dromara.pay.factory;

import jakarta.annotation.Resource;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.pay.domain.vo.PayConfigVo;
import org.dromara.pay.service.IPayConfigService;
import org.dromara.pay.service.IPayStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 16:28
 */
@Component
public class PayStrategyFactory {
    @Resource
    private Map<String, IPayStrategy> payStrategyMap;
    @Resource
    private IPayConfigService configService;

    public IPayStrategy getStrategy(String channel) {
        PayConfigVo config = configService.queryByChannel(channel);
        IPayStrategy iPayStrategy = payStrategyMap.get(channel);
        if (iPayStrategy == null){
            throw new ServiceException("不支持的支付渠道");
        }
        return iPayStrategy;
    }
}
