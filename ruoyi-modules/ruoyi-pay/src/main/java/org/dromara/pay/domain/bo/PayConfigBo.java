package org.dromara.pay.domain.bo;

import org.dromara.pay.domain.PayConfig;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 支付配置业务对象 pay_config
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = PayConfig.class, reverseConvertGenerate = false)
public class PayConfigBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long configId;

    /**
     * 支付渠道
     */
    @NotBlank(message = "支付渠道不能为空", groups = { AddGroup.class, EditGroup.class })
    private String channel;

    /**
     * 应用ID
     */
    @NotBlank(message = "应用ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appId;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * API密钥
     */
    @NotBlank(message = "API密钥不能为空", groups = { AddGroup.class, EditGroup.class })
    private String apiKey;

    /**
     * 证书路径
     */
    private String certPath;

    /**
     * 状态（0正常 1停用）
     */
    @NotBlank(message = "状态（0正常 1停用）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;
}
