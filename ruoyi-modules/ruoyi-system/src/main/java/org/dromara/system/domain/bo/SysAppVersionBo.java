package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysAppVersion;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * App版本信息业务对象 sys_app_version
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysAppVersion.class, reverseConvertGenerate = false)
public class SysAppVersionBo extends BaseEntity {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 版本号
     */
    @NotBlank(message = "版本号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String versionNumber;

    /**
     * 发布日期
     */
    @NotNull(message = "发布日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date releaseDate;

    /**
     * 版本描述
     */
    private String description;

    /**
     * 下载地址
     */
    private String downloadUrl;


}
