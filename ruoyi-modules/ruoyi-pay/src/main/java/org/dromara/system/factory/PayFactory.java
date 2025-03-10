package org.dromara.system.factory;

import org.dromara.common.core.exception.ServiceException;
import org.dromara.system.domain.vo.PayConfigVo;
import org.dromara.system.enums.PayChannelEnum;
import org.dromara.system.service.IPayConfigService;
import org.dromara.system.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 16:28
 */
@Component
public class PayFactory {
    @Autowired
    private IPayConfigService configService;

    public IPayService getPayService(String channel) {
        PayConfigVo config = configService.queryByChannel(channel);
        if (PayChannelEnum.ALIPAY.getCode().equals(channel)) {
            return new AlipayServiceImpl(config);
        }else if (PayChannelEnum.WXPAY.getCode().equals(channel)) {
            return new WxpayServiceImpl(config);
        }
        throw new ServiceException("不支持的支付渠道");
    }
}
