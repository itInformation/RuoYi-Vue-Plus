package org.dromara.system.domain.vo;

import org.dromara.system.domain.PayConfig;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 支付配置视图对象 pay_config
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = PayConfig.class)
public class PayConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ExcelProperty(value = "")
    private Long configId;

    /**
     * 支付渠道
     */
    @ExcelProperty(value = "支付渠道")
    private String channel;

    /**
     * 应用ID
     */
    @ExcelProperty(value = "应用ID")
    private String appId;

    /**
     * 商户号
     */
    @ExcelProperty(value = "商户号")
    private String mchId;

    /**
     * API密钥
     */
    @ExcelProperty(value = "API密钥")
    private String apiKey;

    /**
     * 证书路径
     */
    @ExcelProperty(value = "证书路径")
    private String certPath;

    /**
     * 状态（0正常 1停用）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=正常,1=停用")
    private String status;


}
