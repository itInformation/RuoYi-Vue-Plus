package org.dromara.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.pay.domain.PayConfig;
import org.dromara.pay.domain.PayOrder;
import org.dromara.pay.domain.vo.PayConfigVo;
import org.dromara.pay.service.IPayConfigService;
import org.dromara.pay.service.IPayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
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
@Component("aliPayStrategy")
public class AliPayStrategy implements IPayStrategy {
    private final IPayConfigService payConfigService;
    private AlipayClient client;
    public AlipayClient init() {
        PayConfigVo config = payConfigService.queryByChannel("alipay");
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        alipayConfig.setAppId(config.getAppId());
        alipayConfig.setPrivateKey("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCv94IaOn1HKvrTAksgNH5fxByxi0I/1s42gZHD8/2f/Tsm9AlYqX9SDhTVHfLTy3dO6jH3DquIWWM5BOI3nemRjnar4bCfR5j1t/LmjsBN+pqXDduPQCR2gOzesuUokXHLKwNR/1+uo8nXZzwrD7BivGTg1spj+/eHF3ezCy+6HWbNtyZigjDVjzfcMDzBpOhHXbcpEd/z+JPauKr1AiBiM59cC9uVFCgeGrfhnBgEErqhQTBXtLFZln6upQDC2FLyEDstgqzjtaK0sYB5B4qtjulCZP29VLXvJlbhzOdyGwwj/Oc1ajWF+y47F35zctJAQYa4hjz8UJnyp+m8nizBAgMBAAECggEBAIiSkC6tMKh4yj3VbNttApxvSFngHKYRoPEa9Thh+Xof/7YG9+4Rd5kHBtI1sU8CfvIHwIngn+9ueR07FhxWen6XVLGos/2bgzNK9sJNDyI2m58biUoQXXyAKdfafxNSd0BZfPU6QFf6FWESZdeCEMNBNwZujpy1ZfM6mpOi3gMTHyJ6RajOEmTUBP+gydmSKqHi3YYD+qccOgE1q0yaY9JLAUA0UsC1Lmay2bN1eRu3ujCrKzix308uU96Q0V5HvB9wYiYwH2prHTtWSF2fNyfwd6s9w9R/VqrBdNy7B98skqhQ14PjhosrE95R/c36xagP9ATAyc1jbnFtvq47QIUCgYEA2ytNK66DR8E64+R0/XaYgeEj4FNA5Y3GSONjKwtoG7n59UOhdEJ3FalxCarlBSDHrROKu2gX0pxKEGdnM2IRyXuDraE2bYSuKuev0raD2bS/9C9/ghljnKNUYcBsH0GTBOGlDJIquyKn+FaQC3zvRU5rKLXH0e7VbEI1fRiynwcCgYEAzYmig3ZovKToh04sT0LGMx0OABSnq2RN/6BCyil2BUH0/MPmWEJNWt7ALPtLpJ2K6UYysLAIAtXZ3GOnZLpSJ0jq/i6g7G5KmAtlr0ADPKezGh4eH4qeyhj2Ni9invqZ7SI+5D3rdhD1QCSMdhQW6Vw0jZanDXG9scyhZCE3G/cCgYEAnQKqaE6/EdqmO7jg7uvVWcZWk1BRP6KPmIv98UpcrPBQ15o5wjYTZdtbdZeONlxDcAR98MAjwOfiT1oknrT1FSh9Lxg9C7xvoaEVXpkOYBqTIXYdnLBfokmIG/dj/ghWMLsOKLFWF3Y+azRoQTKhnz8Mmb+QhR7ampHcC+BzpmUCgYAVNQBJpCwGWtWLMGut+evNVlAdfgQW0RCjMXvys/hzbmzCr8vSi3TDzXvyqDMT1xWAIDgWtvL8p+gEr1uULw1O9F0awODlxDN6IKLrRCWRWpRGO5lME9dybsTorlb0pFLBfukST7k9w14qshq0DDqY9WSOv16ZKoUyFTUJycuP3QKBgCLY3BykIuxfGTHBPu5hHgllYCHfJWBxOKSsJcTmq8IEFvKIH7JKlqfwSInQCLPDmnA1CXKaRmoJ3CF0/0xybYCzXsN2MNWdGDSKIZkouBuYt9RGefZnMRx+2kmrWffcIg5JjQUhVJOW2kenD0K1HM7kmAZ7jUlZ+HBwUc1Z07Ol");
        alipayConfig.setPrivateKey(config.getPrivateKey());
        alipayConfig.setFormat("json");
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setAlipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtQdCa/gnzYBbog0FzcYLlgP43hjhs3Wzkdq5pB0SLFbIsDCzUrBf6Aj7AX6nklKxdoGYmILB4rzzELys5AB7rN3jRihuqFJQpfERWzRO+LxWVX7/6fSOugoObU66UcNE2Tf0WRBqsC3UEN+l2ZSRvi2hGq0mhAprzbkRFhxWQqdK6mAWgXjFtYOBZk+4cyu7PPA4oN4Bys3/7TYYhJO+lrBZGYvpAPW7VSslmHsr2S4thdRAm7uYXZaK+EUX7xNstfozWme56E+iEEyKhXGsNyXl4xW1MdtarK60p0y8ib8kvggg8I/QZWzi80PCaC+cdttXQh03y4xu6oz2Jn2sOwIDAQAB");
        alipayConfig.setAlipayPublicKey(config.getPublicKey());

        alipayConfig.setSignType("RSA2");

        try {
            client = new DefaultAlipayClient(alipayConfig);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        return client;
    }
    @Override
    public Map<String, String> createOrder(PayOrder order) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(null);
        request.setNotifyUrl(order.getNotifyUrl());

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(order.getOrderNo());
        model.setTotalAmount(order.getAmount().toString());
        model.setSubject(order.getSubject());
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
    public PayOrder queryOrder(String orderNo) {
        return null;
    }
}
