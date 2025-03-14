package org.dromara.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.Data;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:41 2025/3/10
 * modified by
 */
@Data
@ExcelIgnoreUnannotated
public class QiniuUploadVO {

    private String token;

    private String domain;

    private String region;
}
