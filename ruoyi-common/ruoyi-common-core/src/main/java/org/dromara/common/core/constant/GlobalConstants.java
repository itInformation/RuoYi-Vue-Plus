package org.dromara.common.core.constant;

/**
 * 全局的key常量 (业务无关的key)
 *
 * @author Lion Li
 */
public interface GlobalConstants {
    /**
     * 全局 redis key (业务无关的key)
     */
    String APP_NAME = "lianaimao:";

    /**
     * 全局 redis key (业务无关的key)
     */
    String GLOBAL_REDIS_KEY = APP_NAME + "global:";

    /**
     * 验证码 redis key
     */
    String CAPTCHA_CODE_KEY = GLOBAL_REDIS_KEY + "captcha_codes:";
    String FACE_IDENTITY_TOKEN_CODE_KEY = GLOBAL_REDIS_KEY + "face_identity_token:";
    String FACE_IDENTITY_NONCE_CODE_KEY = GLOBAL_REDIS_KEY + "face_identity_nonce:";
    String FACE_IDENTITY_SIGN_CODE_KEY = GLOBAL_REDIS_KEY + "face_identity_sign:";

    /**
     * 防重提交 redis key
     */
    String REPEAT_SUBMIT_KEY = GLOBAL_REDIS_KEY + "repeat_submit:";

    /**
     * 限流 redis key
     */
    String RATE_LIMIT_KEY = GLOBAL_REDIS_KEY + "rate_limit:";

    /**
     * 三方认证 redis key
     */
    String SOCIAL_AUTH_CODE_KEY = GLOBAL_REDIS_KEY + "social_auth_codes:";
}
