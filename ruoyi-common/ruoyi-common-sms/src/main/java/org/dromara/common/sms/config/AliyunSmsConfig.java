package org.dromara.common.sms.config;

import org.dromara.common.sms.domain.vo.SysSmsConfigVo;
import org.dromara.sms4j.aliyun.config.AlibabaConfig;
import org.dromara.sms4j.core.datainterface.SmsReadConfig;
import org.dromara.sms4j.provider.config.BaseConfig;
import org.dromara.common.sms.enums.SupplierTypeEnum;
import org.dromara.common.sms.service.ISysSmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/10 15:05
 */
@Component(value = "sms_config_aliyun")
public class AliyunSmsConfig implements SmsReadConfig {
    @Autowired
    private ISysSmsConfigService sysSmsConfigService;
    @Override
//    @Cacheable(value = "sms_config", key = "#supplier + '_' + #tenantId")
    public BaseConfig getSupplierConfig(String configId) {
        SysSmsConfigVo sysSmsConfigVo = sysSmsConfigService.queryBySupplier(SupplierTypeEnum.QINIU.getType());
        AlibabaConfig alibabaConfig = new AlibabaConfig();
        alibabaConfig.setSignature(sysSmsConfigVo.getSignature());
        alibabaConfig.setTemplateId(sysSmsConfigVo.getTemplateId());
        alibabaConfig.setAccessKeyId(sysSmsConfigVo.getAccessKey());
        alibabaConfig.setAccessKeySecret(sysSmsConfigVo.getSecretKey());
        return alibabaConfig;
    }

    @Override
    public List<BaseConfig> getSupplierConfigList() {
        return List.of();
    }
}
