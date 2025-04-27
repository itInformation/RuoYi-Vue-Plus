package org.dromara.system.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 会员类型;对象 member_type
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("member_type")
public class MemberType extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类型ID
     */
    @TableId(value = "type_id")
    private Long typeId;

    /**
     * 类型名称（项目/圈子/创作者）
     */
    private String typeName;

    /**
     * 类型描述
     */
    private String desc;

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
