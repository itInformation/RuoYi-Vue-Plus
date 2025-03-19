package org.dromara.circle.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 圈子主体对象 circle_group
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("circle_group")
public class CircleGroup extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 圈子ID
     */
    @TableId(value = "group_id", type = IdType.ASSIGN_ID)
    private String groupId;

    /**
     * 圈子名称
     */
    private String groupName;

    /**
     * 圈子描述
     */
    private String description;

    /**
     * 拥有者ID
     */
    private Long ownerId;

    /**
     * 封面图
     */
    private String coverImg;

    /**
     * 加入方式（0自由加入 1审核加入 2禁止加入）
     */
    private Long joinMode;


    /**
     * 圈子状态 0 启用 1停用
     */
    private Integer status;


    /**
     * 0 未删除 1.已删除 2.回收站
     */
    private Integer deleted;

    /**
     * 0 不在回收站 1.在回收站
     */
    private Integer recycleBin;

}
