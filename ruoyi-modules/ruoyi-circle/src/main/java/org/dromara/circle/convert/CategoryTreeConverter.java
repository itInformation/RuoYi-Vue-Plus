package org.dromara.circle.convert;

import org.dromara.circle.domain.CircleCategory;
import org.dromara.circle.domain.vo.CategoryTreeVO;

import java.util.ArrayList;

/**
 * @description:
 * @author: zhangminghui
 * @email: zhangminghui@gycloud.com
 * @date: 2025/3/20 15:26
 */
public class CategoryTreeConverter {
    /**
     * 将数据库实体转换为树形VO
     */
    public static CategoryTreeVO convertFromEntity(CircleCategory entity) {
        return new CategoryTreeVO(
            entity.getCatId(),
            entity.getCatName(),
            entity.getParentId(),
            entity.getLevel(),
            entity.getSortOrder(),
            entity.getIsHot() == 1,
            entity.getIcon(),
            new ArrayList<>(),0,0
        );
    }

    /**
     * 补充统计字段（可选）
     */
    public static CategoryTreeVO withCount(CategoryTreeVO vo, Integer groupCount) {
        // 可扩展添加统计字段
        return vo; // 实际实现需修改VO结构
    }
}
