package org.dromara.circle.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 资源文件对象 circle_resource
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("circle_resource")
public class CircleResource extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    @TableId(value = "resource_id", type = IdType.ASSIGN_ID)
    private String resourceId;

    /**
     * 关联内容ID
     */
    private String contentId;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 存储路径
     */
    private String filePath;


}
