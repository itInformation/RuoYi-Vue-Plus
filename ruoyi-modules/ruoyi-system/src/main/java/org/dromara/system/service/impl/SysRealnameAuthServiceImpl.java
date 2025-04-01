package org.dromara.system.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.Constants;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.translation.core.impl.OssUrlTranslationImpl;
import org.dromara.system.domain.SysRealnameAuth;
import org.dromara.system.domain.bo.SysRealnameAuthBo;
import org.dromara.system.domain.vo.SysOssVo;
import org.dromara.system.domain.vo.SysRealnameAuthVo;
import org.dromara.system.mapper.SysRealnameAuthMapper;
import org.dromara.system.service.ISysOssService;
import org.dromara.system.service.ISysRealnameAuthService;
import org.dromara.system.util.TengxunFaceAuth;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 实名认证信息 Service业务层处理
 *
 * @author Lion Li
 * @date 2025-04-01
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysRealnameAuthServiceImpl implements ISysRealnameAuthService {

    private final SysRealnameAuthMapper baseMapper;
    private final ISysOssService sysOssService;

    /**
     * 查询实名认证信息
     *
     * @param authId 主键
     */
    @Override
    public SysRealnameAuthVo queryById(Long authId) {
        return baseMapper.selectVoById(authId);
    }

    public SysRealnameAuthVo queryByUserId(Long userId) {
        SysRealnameAuthBo sysRealnameAuthBo = new SysRealnameAuthBo();
        sysRealnameAuthBo.setUserId(userId);
        return baseMapper.selectVoOne(buildQueryWrapper(sysRealnameAuthBo));
    }


    /**
     * 分页查询实名认证信息 列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 实名认证信息 分页列表
     */
    @Override
    public TableDataInfo<SysRealnameAuthVo> queryPageList(SysRealnameAuthBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysRealnameAuth> lqw = buildQueryWrapper(bo);
        Page<SysRealnameAuthVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的实名认证信息 列表
     *
     * @param bo 查询条件
     * @return 实名认证信息 列表
     */
    @Override
    public List<SysRealnameAuthVo> queryList(SysRealnameAuthBo bo) {
        LambdaQueryWrapper<SysRealnameAuth> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysRealnameAuth> buildQueryWrapper(SysRealnameAuthBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysRealnameAuth> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(SysRealnameAuth::getAuthId);
        lqw.eq(bo.getUserId() != null, SysRealnameAuth::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getRealName()), SysRealnameAuth::getRealName, bo.getRealName());
        lqw.eq(StringUtils.isNotBlank(bo.getIdNumber()), SysRealnameAuth::getIdNumber, bo.getIdNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getIdcardFront()), SysRealnameAuth::getIdcardFront, bo.getIdcardFront());
        lqw.eq(StringUtils.isNotBlank(bo.getIdcardBack()), SysRealnameAuth::getIdcardBack, bo.getIdcardBack());
        lqw.eq(StringUtils.isNotBlank(bo.getFaceData()), SysRealnameAuth::getFaceData, bo.getFaceData());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), SysRealnameAuth::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增实名认证信息
     *
     * @param bo 实名认证信息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysRealnameAuthBo bo) {
        SysRealnameAuth add = MapstructUtils.convert(bo, SysRealnameAuth.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAuthId(add.getAuthId());
        }
        return flag;
    }

    /**
     * 修改实名认证信息
     *
     * @param bo 实名认证信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysRealnameAuthBo bo) {
        SysRealnameAuth update = MapstructUtils.convert(bo, SysRealnameAuth.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysRealnameAuth entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除实名认证信息 信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    public String startIdentity(Long userId, String orderNo, String optionDomain, String callBackUrl, String h5FaceId) {
        //1. 获取accessToken
        String accessToken = getAccessToken();
        //3. 获取NONCE ticket
        String nonceTicket = TengxunFaceAuth.getNonceTicket(accessToken, userId);
        //4.上传身份信息 需要签名
        //5. 启用h5人脸验证 需要签名
        String startSign = TengxunFaceAuth.getStartSign(userId, orderNo, nonceTicket, h5FaceId);
        return TengxunFaceAuth.buildIdentity(optionDomain, userId, callBackUrl, orderNo, h5FaceId, startSign);
        //6. 回调
        //7. 获取h5人脸验证结果
    }

    @Override
    public String uploadIdentityInfo(Long userId) {
        SysRealnameAuthVo sysRealnameAuthVo = queryByUserId(userId);
        if (sysRealnameAuthVo == null) {
            throw new ServiceException("实名认证信息上传有误");
        }
        //1. 获取accessToken
        String accessToken = getAccessToken();
        //2. 获取SIGN ticket
        String signTicket = getSignTicket(accessToken);
        SysOssVo sysOssVo = sysOssService.getById(Long.valueOf(sysRealnameAuthVo.getIdcardFront()));
        if (sysOssVo == null) {
            throw new ServiceException("实名认证信息上传有误");
        }


        //4.上传身份信息 需要签名
        try {

            String base64Img = Base64.getEncoder().encodeToString(HttpUtil.get(sysOssVo.getUrl()).getBytes());
            return TengxunFaceAuth.uploadIdentityInfo(sysRealnameAuthVo.getAuthId(), userId, sysRealnameAuthVo.getRealName(), sysRealnameAuthVo.getIdNumber(), base64Img, "2", signTicket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void identityCallback(String code, String orderNo, String h5faceId, String newSign) {
        log.info("回调成功, code: {}, orderNo: {}, h5faceId: {}, newSign: {}", code, orderNo, h5faceId, newSign);
    }

    @Override
    public JSONObject queryIdentityResult(String orderNo) {
        //1. 获取accessToken
        String accessToken = getAccessToken();
        //2. 获取SIGN ticket
        String signTicket = getSignTicket(accessToken);
        String resultSign = TengxunFaceAuth.getResultSign(orderNo, signTicket);
        return new JSONObject(TengxunFaceAuth.queryIdentityResult(orderNo, resultSign));
    }

    /**
     * 获取accessToken
     */
    private String getAccessToken() {
        String key = GlobalConstants.FACE_IDENTITY_TOKEN_CODE_KEY;
        String accessToken = RedisUtils.getCacheObject(key);
        if (StringUtils.isEmpty(accessToken)) {

            accessToken = TengxunFaceAuth.getAccessToken();
            RedisUtils.setCacheObject(key, accessToken, Duration.ofMinutes(Constants.FACE_ACCESS_TOKEN_EXPIRATION));
        }
        return accessToken;
    }


    /**
     * 获取accessToken
     */
    private String getSignTicket(String accessToken) {
        String key = GlobalConstants.FACE_IDENTITY_SIGN_CODE_KEY;
        String signTicket = RedisUtils.getCacheObject(key);
        if (StringUtils.isEmpty(signTicket)) {

            signTicket = TengxunFaceAuth.getSignTicket(accessToken);
            RedisUtils.setCacheObject(key, signTicket, Duration.ofMinutes(Constants.FACE_ACCESS_SIGN_EXPIRATION));
        }
        return signTicket;
    }
}
