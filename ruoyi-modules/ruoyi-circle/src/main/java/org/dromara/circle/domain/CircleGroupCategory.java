package org.dromara.circle.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 圈子-分类关系对象 circle_group_category
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("circle_group_category")
public class CircleGroupCategory extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 圈子ID
     */
    private String groupId;

    /**
     * 分类ID
     */
    private Long catId;

    /**
     * 数据删除状态 0 未删除 1.删除 2.放入回收站
     */
    @TableLogic
    private Long deleted;

    public CircleGroupCategory(String groupId, Long catId) {
        this.groupId = groupId;
        this.catId = catId;
    }
    public CircleGroupCategory() {
    }
}
