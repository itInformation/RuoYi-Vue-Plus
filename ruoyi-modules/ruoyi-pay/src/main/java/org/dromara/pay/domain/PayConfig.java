package org.dromara.pay.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 支付配置对象 pay_config
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pay_config")
public class PayConfig extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "config_id")
    private Long configId;

    /**
     * 支付渠道
     */
    private String channel;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * API密钥
     */
    private String apiKey;

    /**
     * 证书路径
     */
    private String certPath;

    /**
     * 状态（0正常 1停用）
     */
    private String status;


}
