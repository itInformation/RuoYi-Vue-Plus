package org.dromara.system.domain.bo;

import org.dromara.system.domain.SysVipLevelUser;
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
 * 用户等级业务对象 sys_vip_level_user
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysVipLevelUser.class, reverseConvertGenerate = false)
public class SysVipLevelUserBo extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 类型id
     */
    private Long typeId;

    /**
     * 当前等级
     */
    private String currentLevel;

    /**
     * 过期时间
     */
    private Date expireTime;

    /**
     * 圈子id
     */
    private String circleId;

    /**
     * 创作者id
     */
    private Long creatorId;

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
