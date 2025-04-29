package org.dromara.pay.utils;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/4/29 11:05
 */
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.UUID;
@Component
public class RefundRequestNoGenerator {

    // 设置退款请求号的最大长度
    private static final int MAX_LENGTH = 64;

    /**
     * 生成唯一的退款请求号
     *
     * @return 生成的退款请求号
     */
    public String generateRefundRequestNo() {
        // 使用当前时间戳（年月日时分秒）和一个唯一标识符（UUID）来生成请求号
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8); // 获取 UUID 的前 8 个字符

        // 组合时间戳和 UUID，构成退款请求号
        String refundRequestNo = timestamp + uuid;

        // 如果长度超过最大限制，则截取为最大长度
        if (refundRequestNo.length() > MAX_LENGTH) {
            refundRequestNo = refundRequestNo.substring(0, MAX_LENGTH);
        }

        return refundRequestNo;
    }
}
