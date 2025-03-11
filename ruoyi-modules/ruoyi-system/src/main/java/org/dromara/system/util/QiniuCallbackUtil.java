package org.dromara.system.util;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午11:20 2025/3/10
 * modified by
 */

import com.qiniu.util.Auth;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.oss.core.OssClient;
import org.dromara.common.oss.factory.OssFactory;
import org.dromara.common.oss.properties.OssProperties;
import org.dromara.common.sms.enums.SupplierTypeEnum;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

;

public class QiniuCallbackUtil {

    private static final String QINIU_CALLBACK_AUTH_HEADER = "Authorization";
    private static final String QINIU_CALLBACK_AUTH_PREFIX = "QBox ";

    /**
     * 验证七牛云回调请求的合法性
     *
     * @param request HttpServletRequest 对象
     * @return boolean 是否验证通过
     */
    public static boolean verifyCallback(HttpServletRequest request) {
        // 1. 从Header提取Authorization
        String authHeader = request.getHeader(QINIU_CALLBACK_AUTH_HEADER);
        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith(QINIU_CALLBACK_AUTH_PREFIX)) {
            return false;
        }
        String qiniuSignature = authHeader.substring(QINIU_CALLBACK_AUTH_PREFIX.length());

        // 2. 构建待签名的原始数据
        String callbackUrl = request.getRequestURL().toString();
        String callbackBody = getRequestBody(request);
        if (callbackBody == null) {
            return false;
        }
        // 3. 获取回调的 URL 路径
        OssClient instance = OssFactory.instance(SupplierTypeEnum.QINIU.getType());
        if (SupplierTypeEnum.QINIU.getType().equals(instance.getConfigKey())){
            throw new ServiceException("当前七牛云云存储有误，请检查");
        }
        OssProperties qiniuConfig = instance.getProperties();
        // Step 2: 提取并分割access key和签名
        String[] authParts = authHeader.substring(5).split(":", 2);
        if (authParts.length != 2 || !qiniuConfig.getAccessKey().equals(authParts[0])) {
            return false;
        }
        String receivedSign = authParts[1];
        // Step 3: 构建签名数据
        String requestBody = getRequestBody(request);
        String data = request.getRequestURL().toString() + "\n" + requestBody;
        String calculatedSign = calculateSignature(data, qiniuConfig.getAccessKey());
        return calculatedSign.equals(receivedSign);
    }

    /**
     * 从 HttpServletRequest 中读取请求体
     *
     * @param request HttpServletRequest 对象
     * @return String 请求体内容
     */
    private static String getRequestBody(HttpServletRequest request) {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




    private static String calculateSignature(String data, String secretKey) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA1");
            hmac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
            byte[] hash = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // 转换为URL安全的Base64（保留填充等号）
            String base64 = Base64.getEncoder().encodeToString(hash);
            return base64.replace('+', '-').replace('/', '_');
        } catch (Exception e) {
            throw new RuntimeException("计算签名失败", e);
        }
    }
}
