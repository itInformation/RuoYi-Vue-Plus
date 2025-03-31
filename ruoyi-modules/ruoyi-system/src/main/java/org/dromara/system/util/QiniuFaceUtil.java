package org.dromara.system.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import org.dromara.common.json.utils.JsonUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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


    public static Response face(Client client,Auth auth,String imgUrl) throws QiniuException {
        StringMap headers = new StringMap();
        String imgStr = getImgStr(imgUrl);
        headers.put("Authorization", qiniuToken(auth,null));
        return client.post(url, json(imgStr,null).getBytes(), headers, Client.JsonMime);
    }

    static String qiniuToken(Auth auth,String url) {
        return auth.qiniuAuthorization(url, "POST", json(null,null).getBytes(), null).get("Authorization");
    }
    public static String getImgStr(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        FileInputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
            return Base64.encodeToString(data,Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String json(String imageA,String imageB) {
        return "{\"min_code_length\":1,\"max_code_length\":3}";
    }

    public static Response face(String imgUrl) throws QiniuException {
        Auth auth = Auth.create(key, secret);
        StringMap headers = new StringMap();
        String url = "https://face-actlive.qiniuapi.com/v2/actionliveness/sessioncode";
//        String imgStr = getImgStr(imgUrl);
        headers.put("Authorization", qiniuToken(auth,url));
        headers.put("Content-Type", "application/json");
        Client client = new Client();
        return client.post(url, json(null,null).getBytes(), headers, Client.JsonMime);
    }

    public static void main(String[] args) throws QiniuException {
        Response face = face(null);
        System.out.println(face);
    }

}
