package org.dromara.system.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * App版本信息对象 sys_app_version
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_app_version")
public class SysAppVersion extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 版本号
     */
    private String versionNumber;

    /**
     * 发布日期
     */
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
