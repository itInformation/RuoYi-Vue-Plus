package org.dromara.system.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TengxunFaceAuth {

    private static final String appId = "TIDAXX0h" ;
    private static final String appSecret = "FNMpvCya0MUXWEtJHuqA7VtxnJPlBdWKa9ZjSUPoQ1QULGToDq31PA9oLxdNZM9X" ;

    /**
     * 获取accessToken
     */
    public static String getAccessToken() {
        String url = "https://kyc1.qcloud.com/api/oauth2/access_token?" + "appId=" +
            appId +
            "&secret=" +
            appSecret +
            "&grant_type=client_credential" +
            "&version=1.0.0" ;
        String result = HttpUtil.get(url);
        return new JSONObject(result).getStr("access_token");
    }

    /**
     * 获取SIGN ticket
     */
    public static String getSignTicket(String accessToken) {
        String url = "https://kyc1.qcloud.com/api/oauth2/api_ticket?" + "appId=" +
            appId +
            "&access_token=" +
            accessToken +
            "&type=SIGN" +
            "&version=1.0.0" ;
        String result = HttpUtil.get(url);
        JSONObject ticketObject = (JSONObject)new JSONObject(result).getJSONArray("tickets").get(0);
        return ticketObject.getStr("value");
    }

    /*
     * 获取NONCE ticket
     */
    public static String getNonceTicket(String accessToken, Long userId) {
        String url = "https://kyc1.qcloud.com/api/oauth2/api_ticket?" + "appId=" +
            appId +
            "&access_token=" +
            accessToken +
            "&type=NONCE" +
            "&version=1.0.0" +
            "&user_id=" +
            userId;
        String result = HttpUtil.get(url);
        JSONObject ticketObject = (JSONObject)new JSONObject(result).getJSONArray("tickets").get(0);
        return ticketObject.getStr("value");
    }

    /**
     * 后台上传信息生成签名
     */
    public static String getUploadSign(Long userId, String realName, String idCard, String orderNo, String signTicket) {
        List<String> values = new ArrayList<>();
        values.add(appId);
        values.add(String.valueOf(userId));
        values.add(realName);
        values.add(idCard);
        values.add(orderNo);
        values.add("1.0.0");
        return SignUtil.sign(values, signTicket);

    }

    /**
     * 合作方后台上传身份信息
     */
    public static String uploadIdentityInfo(String orderNo, Long userId, String realName, String idCard, String sourcePhotoStr, String sourcePhotoType, String signTicket) throws IOException {
        String sign = getUploadSign(userId, realName, idCard, orderNo, signTicket);
        String url = "https://kyc1.qcloud.com/api/server/h5/geth5faceid?orderNo=" + orderNo;
        // 带JSON参数的POST请求
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appId", appId);
        paramMap.put("orderNo", orderNo);
        paramMap.put("name", realName);
        paramMap.put("idNo", idCard);
        paramMap.put("userId", userId);
        paramMap.put("sourcePhotoStr", sourcePhotoStr);
        paramMap.put("sourcePhotoType", sourcePhotoType);
        paramMap.put("version", "1.0.0");
        paramMap.put("sign", sign);
        return HttpUtil.post(url, JSONUtil.toJsonStr(paramMap));

    }

    /**
     * 启用h5生成签名
     */
    public static String getStartSign(Long userId, String orderNo, String nonceTicket, String h5FaceId) {
        List<String> values = new ArrayList<>();
        values.add(appId);
        values.add(String.valueOf(userId));
        String nonce = SignUtil.generateRandomString(32);
        values.add(nonce);
        values.add("1.0.0");
        values.add(h5FaceId);
        values.add(orderNo);
        return SignUtil.sign(values, nonceTicket);

    }

    /**
     * 启用h5人脸验证
     */
    public static String starIdentity(String domain, Long userId, String callBackUrl, String orderNo,  String h5FaceId, String sign) {
        return HttpUtil.get(buildIdentity(domain,userId,callBackUrl,orderNo,h5FaceId,sign));


    }
    public static String buildIdentity(String domain, Long userId, String callBackUrl, String orderNo,  String h5FaceId, String sign) {
        String accessDomain = domain;
        if (StringUtils.isEmpty(accessDomain)) {
            accessDomain = "kyc1.qcloud.com" ;
        }
        String url = "https://" + accessDomain + "/api/pc/login?appId=" + appId +
            "&version=1.0.0" +
            "&nonce=" +
            SignUtil.generateRandomString(32) +
            "&orderNo=" +
            orderNo +
            "&h5faceId=" +
            h5FaceId +
            "&url=" +
            URLDecoder.decode(callBackUrl, StandardCharsets.UTF_8) +
            "&userId=" +
            userId +
            "&sign=" +
            sign;
        return url;


    }


    /**
     * 启用h5生成签名
     */
    public static String getResultSign( String orderNo, String signTicket) {
        List<String> values = new ArrayList<>();
        values.add(appId);
        values.add(orderNo);
        values.add("1.0.0");
        String nonce = SignUtil.generateRandomString(32);
        values.add(nonce);
        return SignUtil.sign(values, signTicket);

    }
    public static String queryIdentityResult(String orderNo,String sign){
        String url = "https://kyc1.qcloud.com/api/v2/base/queryfacerecord?orderNo=" + orderNo;
        // 带JSON参数的POST请求
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appId", appId);
        paramMap.put("version", "1.0.0");
        paramMap.put("nonce",SignUtil.generateRandomString(32));
        paramMap.put("orderNo", orderNo);
        paramMap.put("sign", sign);
        return HttpUtil.post(url, JSONUtil.toJsonStr(paramMap));

    }
}
