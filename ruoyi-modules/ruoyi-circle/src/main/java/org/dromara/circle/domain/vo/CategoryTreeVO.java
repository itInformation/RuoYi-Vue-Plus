package org.dromara.circle.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/20 15:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTreeVO {
    /**
     * 分类ID
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 层级（1-一级分类 2-二级分类...）
     */
    private Long level;

    /**
     * 排序权重
     */
    private Long sortOrder;

    /**
     * 是否热门分类
     */
    private Boolean hot;

    /**
     * 分类图标地址
     */
    private String icon;

    /**
     * 子分类列表
     */
    private List<CategoryTreeVO> children;
    // 新增统计字段
    private Integer groupCount; // 该分类下圈子数量
    private Integer viewCount; // 该分类浏览量



    /**
     * 判断是否为叶子节点
     */
    public boolean isLeaf() {
        return children == null || children.isEmpty();
    }
}
