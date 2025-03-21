package org.dromara.circle.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 用户分类兴趣权重对象 user_category_weight
 *
 * @author Lion Li
 * @date 2025-03-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_category_weight")
public class UserCategoryWeight extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 分类ID
     */
    private Long catId;

    /**
     * 权重值
     */
    private Long weight;

    /**
     * 最后活跃时间
     */
    private Date lastActive;

    /**
     * 版本号
     */
    @Version
    private Long version;


}
