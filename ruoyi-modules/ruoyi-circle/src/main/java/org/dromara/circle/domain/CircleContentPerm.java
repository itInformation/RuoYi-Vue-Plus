package org.dromara.circle.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 内容权限关联对象 circle_content_perm
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("circle_content_perm")
public class CircleContentPerm extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 内容ID
     */
    @TableId(value = "content_id", type = IdType.ASSIGN_ID)
    private String contentId;

    /**
     * 允许查看的用户ID
     */

    private Long userId;


}
