package org.dromara.common.sms.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 短信配置对象 sys_sms_config
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Data
@EqualsAndHashCode()
@TableName("sys_sms_config")
public class SysSmsConfig  {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 配置主键
     */
    @TableId(value = "config_id")
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
