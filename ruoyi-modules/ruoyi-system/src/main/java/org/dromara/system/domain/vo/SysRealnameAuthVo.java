package org.dromara.system.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.system.domain.SysRealnameAuth;
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
 * 【请填写功能名称】视图对象 sys_realname_auth
 *
 * @author Lion Li
 * @date 2025-04-01
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRealnameAuth.class)
public class SysRealnameAuthVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 认证ID
     */
    @ExcelProperty(value = "认证ID")
    private String authId;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 真实姓名
     */
    @ExcelProperty(value = "真实姓名")
    private String realName;

    /**
     * 身份证号
     */
    @ExcelProperty(value = "身份证号")
    private String idNumber;

    /**
     * 身份证正面
     */
    @ExcelProperty(value = "身份证正面")
    @Translation(type = TransConstant.OSS_ID_TO_URL)
    private String idcardFront;

    /**
     * 身份证反面
     */
    @ExcelProperty(value = "身份证反面")
    @Translation(type = TransConstant.OSS_ID_TO_URL)
    private String idcardBack;

    /**
     * 人脸特征数据
     */
    @ExcelProperty(value = "人脸特征数据")
    private String faceData;

    /**
     * 认证状态（0待认证 1通过 2失败）
     */
    @ExcelProperty(value = "认证状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=待认证,1=通过,2=失败")
    private String status;


}
