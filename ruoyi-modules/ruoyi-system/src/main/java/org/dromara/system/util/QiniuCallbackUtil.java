package org.dromara.system.util;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午11:20 2025/3/10
 * modified by
 */
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import jakarta.servlet.http.HttpServletRequest;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.oss.core.OssClient;
import org.dromara.common.oss.factory.OssFactory;
import org.dromara.common.oss.properties.OssProperties;
import org.dromara.common.sms.enums.SupplierTypeEnum;;
import java.io.BufferedReader;
import java.io.IOException;

public class QiniuCallbackUtil {

    private static final String QINIU_CALLBACK_AUTH_HEADER = "Authorization";

    /**
     * 验证七牛云回调请求的合法性
     *
     * @param request HttpServletRequest 对象
     * @return boolean 是否验证通过
     */
    public static boolean verifyCallback(HttpServletRequest request) {
        // 1. 从请求头中获取七牛云的签名
        String authHeader = request.getHeader(QINIU_CALLBACK_AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith("QBox ")) {
            return false; // 签名格式不正确
        }
        String qiniuSignature = authHeader.substring(5); // 去掉 "QBox " 前缀

        // 2. 获取回调的请求体
        String callbackBody = getRequestBody(request);
        if (callbackBody == null) {
            return false; // 请求体为空
        }

        // 3. 获取回调的 URL 路径
        String callbackUrl = request.getRequestURL().toString();
        OssClient instance = OssFactory.instance(SupplierTypeEnum.QINIU.getType());
        if (SupplierTypeEnum.QINIU.getType().equals(instance.getConfigKey())){
            throw new ServiceException("当前七牛云云存储有误，请检查");
        }
        OssProperties qiniuConfig = instance.getProperties();
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        boolean isValid = auth.isValidCallback(authHeader, callbackUrl, callbackBody.getBytes(), request.getContentType());
        return isValid;
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
}
