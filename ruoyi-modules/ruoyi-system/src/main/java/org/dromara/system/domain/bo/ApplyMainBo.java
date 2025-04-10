package org.dromara.system.domain.bo;

import org.dromara.system.domain.ApplyMain;
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
 * 入驻申请主业务对象 apply_main
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ApplyMain.class, reverseConvertGenerate = false)
public class ApplyMainBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long applyId;

    /**
     * 用户id
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 1-个人 2-公会
     */
    @NotBlank(message = "1-个人 2-公会不能为空", groups = { AddGroup.class, EditGroup.class })
    private String applyType;

    /**
     * 审核状态 0 待审核 1 审核通过 2 审核失败
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     *  备注
     */
    private String auditComment;
    /**
     * 达人申请
     */

    private ApplyPersonalBo applyPersonalBo;

    /**
     * 工会申请
     */
    private ApplyGuildBo applyGuildBo;


}
