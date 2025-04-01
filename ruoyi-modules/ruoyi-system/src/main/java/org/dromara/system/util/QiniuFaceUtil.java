package org.dromara.system.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午11:20 2025/3/10
 * modified by
 */

public class QiniuFaceUtil {

    private static final String url = "https://face-detect.qiniuapi.com/facedetect";
    private static final String key = "1mRcadYnuCAPimyWL8apVwUetkfkag0sJ-4McjLV";
    private static final String secret = "bY9yVvVh1zXjhWUBA2bZO34rB68knwAlFdcfvKyK";




    static String json() {
        return "{\"real_name\":\"张明辉\",\"id_card\":\"610202199202262817\"}";

    }
    public static Response face() throws QiniuException, NoSuchAlgorithmException, InvalidKeyException {

        String src = "lianaimao:10000";
        String srcBase64 = UrlSafeBase64.encodeToString(src);
        String sign = hmac_sha1(srcBase64, key);
        String encodedSign = UrlSafeBase64.encodeToString(sign);
        String token = "QD " + key + ":" + encodedSign + ":" + srcBase64;
        StringMap headers = new StringMap();
        String url = "https://ap-open-z0.qiniuapi.com/faceid-sdk/api/face-hdphotoauth/token";
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json");
        Client client = new Client();
        return client.post(url, json().getBytes(), headers, Client.JsonMime);
    }

    public static void main(String[] args) throws QiniuException, NoSuchAlgorithmException, InvalidKeyException {
        Response face = face();
        System.out.println(face);

    }

    public static String hmac_sha1(String data,String key)
        throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeyException {
        // 获取 HMAC-SHA1 算法的 Mac 实例
        Mac mac = Mac.getInstance("HmacSHA1");

        // 将密钥字符串转换为 SecretKeySpec
        SecretKeySpec secretKey = new SecretKeySpec(
            key.getBytes(StandardCharsets.UTF_8),
            "HmacSHA1"
        );

        // 初始化 Mac 实例
        mac.init(secretKey);

        // 计算 HMAC
        byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // 将字节数组转换为十六进制字符串
        return bytesToHex(rawHmac);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = String.format("%02x", b & 0xFF);
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
