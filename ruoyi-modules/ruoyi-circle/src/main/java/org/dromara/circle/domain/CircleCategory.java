package org.dromara.circle.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 圈子分类对象 circle_category
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("circle_category")
public class CircleCategory extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @TableId(value = "cat_id")
    private Long catId;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String catName;

    /**
     * 排序
     */
    private Long sortOrder;

    /**
     * 层级
     */
    private Long level;

    /**
     * 是否热门分类
     */
    private Long isHot;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 数据删除状态 0 未删除 1.删除 2.放入回收站
     */
    private Long deleted;

    /**
     * 关联圈子数量
     */
    private Integer groupCount;


}
