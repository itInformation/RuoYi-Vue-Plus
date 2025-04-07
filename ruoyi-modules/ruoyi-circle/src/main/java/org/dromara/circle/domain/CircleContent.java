package org.dromara.circle.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 圈子内容对象 circle_content
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("circle_content")
public class CircleContent extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 内容ID
     */
    @TableId(value = "content_id", type = IdType.ASSIGN_ID)
    private String contentId;

    /**
     * 所属圈子ID
     */
    private String groupId;

    /**
     * 作者ID
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型（1视频 2图文 3文字）
     */
    private String contentType;

    /**
     * 内容（JSON存储）
     */
    private String content;

    /**
     * 权限类型（0免费 1会员 2指定）
     */
    private String permType;


    /**
     * 审核状态（0待审核 1审核通过 2审核不通过）
     */
    private Integer review;
    /**
     * 是否置顶
     */
    private Boolean isTop;

    /**
     * 置顶时间
     */
    private LocalDateTime topTime;



    /**
     * 置顶时间
     */
    private LocalDateTime publishTime;
}
