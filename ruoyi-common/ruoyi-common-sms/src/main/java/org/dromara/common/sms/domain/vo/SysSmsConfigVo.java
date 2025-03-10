package org.dromara.common.sms.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.sms.domain.SysSmsConfig;

import java.io.Serial;
import java.io.Serializable;


/**
 * 短信配置视图对象 sys_sms_config
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Data
@AutoMapper(target = SysSmsConfig.class)
public class SysSmsConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 配置主键
     */
    private Long configId;

    /**
     * 厂商类型（ALIBABA/TENCENT/...）
     */
    private String supplier;

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 短信签名
     */
    private String signature;

    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 租户编号
     */
    private String tenantId;
}
