package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysVipUserPoints;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 积分记录业务对象 sys_vip_user_points
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysVipUserPoints.class, reverseConvertGenerate = false)
public class SysVipUserPointsBo extends BaseEntity {

    /**
     * 积分id
     */
    @NotNull(message = "积分id不能为空", groups = { EditGroup.class })
    private Long pointsId;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 创作者id
     */
    @NotNull(message = "创作者id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long typeId;

    /**
     * 当前积分
     */
    private Long currentPoints;

    /**
     * 历史积分
     */
    private Long totalPoints;

    /**
     * 创作者id
     */
    @NotNull(message = "创作者id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long creatorId;

    /**
     * 圈子id
     */
    @NotBlank(message = "圈子id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String circleId;

    /**
     * 乐观锁
     */
    private Long revision;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;


}
