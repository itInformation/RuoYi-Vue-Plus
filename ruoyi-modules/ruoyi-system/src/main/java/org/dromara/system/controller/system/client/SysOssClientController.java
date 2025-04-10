package org.dromara.system.controller.system.client;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.utils.file.MimeTypeUtils;
import org.dromara.common.core.validate.QueryGroup;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.oss.core.OssClient;
import org.dromara.common.oss.factory.OssFactory;
import org.dromara.common.oss.properties.OssProperties;
import org.dromara.common.sms.enums.SupplierTypeEnum;
import org.dromara.common.web.core.BaseController;
import org.dromara.system.domain.bo.SysOssBo;
import org.dromara.system.domain.vo.AvatarVo;
import org.dromara.system.domain.vo.QiniuUploadVO;
import org.dromara.system.domain.vo.SysOssUploadVo;
import org.dromara.system.domain.vo.SysOssVo;
import org.dromara.system.service.ISysOssService;
import org.dromara.system.util.QiniuCallbackUtil;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 文件上传 app端
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/client/resource/oss")
@Slf4j
public class SysOssClientController extends BaseController {

    private final ISysOssService ossService;
    /**
     * 七牛云oss客户端上传凭证返回
     *
     * 上传目录
     * {模块名}/{日期}/{随机ID}.{后缀}
     * /public/        # 公开资源（需CDN加速）
     *   /images/      # 图片类
     *   /videos/      # 视频类
     * /private/       # 私有资源（需签名访问）
     *   /documents/   # 用户文档
     * /temp/
     */
    @SaCheckPermission("client:oss:qiqiuToken")
    @GetMapping("/getQiniuUploadToken")
    public R<QiniuUploadVO> getUploadToken() {

        OssClient instance = OssFactory.instance(SupplierTypeEnum.QINIU.getType());
        if (!SupplierTypeEnum.QINIU.getType().equals(instance.getConfigKey())){
            throw new ServiceException("当前七牛云云存储有误，请检查");
        }
        OssProperties qiniuConfig = instance.getProperties();
//        https://developer.qiniu.com/kodo/1239/java#upload-token
        // 2. 生成七牛云上传凭证（使用SDK）
        StringMap policy = new StringMap();
        policy.put("mimeLimit", "image/*;video/*"); // 限制文件类型
        policy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        policy.put("callbackUrl", "http://api.omuu.cn/prod-api/resource/oss/qiniu-upload-callback");
        policy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
//        policy.put("callbackBodyType", "application/json");
        policy.put("callbackBodyType", "application/x-www-form-urlencoded");
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucketName(), null, 3600, policy);

        // 3. 返回前端需要的信息
        QiniuUploadVO vo = new QiniuUploadVO();
        vo.setToken(upToken);
        vo.setDomain(qiniuConfig.getDomain());
        vo.setRegion(qiniuConfig.getRegion());
        return R.ok(vo);
    }

    /**
     * 用户上传文件
     *
     * @param file 用户头像
     */
    @SaCheckPermission("client:file:upload")
    @RepeatSubmit
    @Log(title = "用户上传文件", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<AvatarVo> uploadFile(@RequestPart("avatarfile") MultipartFile file) {
        if (!file.isEmpty()) {
            String extension = FileUtil.extName(file.getOriginalFilename());
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return R.fail("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
            }
            SysOssVo oss = ossService.upload(file,"storage-face");
            AvatarVo avatarVo = new AvatarVo();
            avatarVo.setImgUrl(oss.getUrl());
            avatarVo.setOssId(oss.getOssId());
            return R.ok(avatarVo);
        }
        return R.fail("上传图片异常，请联系管理员");
    }


    /**
     * 用户上传文件,按照configKey
     *
     * @param file 用户头像
     */
    @SaCheckPermission("client:file:upload")
    @RepeatSubmit
    @Log(title = "用户上传文件", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/uploadFileWithKey", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<AvatarVo> uploadFile(@RequestPart("avatarfile") MultipartFile file,String configKey) {
        if (!file.isEmpty()) {
            String extension = FileUtil.extName(file.getOriginalFilename());
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return R.fail("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
            }
            SysOssVo oss = ossService.upload(file,configKey);
            AvatarVo avatarVo = new AvatarVo();
            avatarVo.setImgUrl(oss.getUrl());
            avatarVo.setOssId(oss.getOssId());
            return R.ok(avatarVo);
        }
        return R.fail("上传图片异常，请联系管理员");
    }
    /**
     * 七牛云上传完成回调配置
      */
    @PostMapping("/qiniu-upload-callback")
    @SaIgnore
    public R<?> handleCallback(HttpServletRequest request) {


        // 1. 验证回调签名
        boolean isValid = QiniuCallbackUtil.verifyCallback(request);
        if (!isValid) return R.fail("非法回调");
        log.info("qiniu-upload-callback: request param: {}",request.getParameterMap().toString());
        // 2. 解析回调数据
        String fileName = request.getParameter("key");
        long fileSize = Long.parseLong(request.getParameter("fsize"));
        String mimeType = request.getParameter("mimeType");

        // 3. 记录到数据库
        SysOssBo record = new SysOssBo();

//        record.setFileName(fileName);
//        record.setFileSize(fileSize);
//        record.set(mimeType);
//        record.setCreateBy(getLoginUserId());
//        fileRecordService.insertRecord(record);

        return R.ok();
    }

}
