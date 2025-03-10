package org.dromara.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.system.domain.CircleApply;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:41 2025/3/10
 * modified by
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleApply.class)
public class QiniuUploadVO {

    private String token;

    private String domain;

    private String region;
}
