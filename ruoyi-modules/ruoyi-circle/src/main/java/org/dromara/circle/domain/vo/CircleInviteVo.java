package org.dromara.circle.domain.vo;

import org.dromara.circle.domain.CircleInvite;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 邀请记录视图对象 circle_invite
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleInvite.class)
public class CircleInviteVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String inviteId;

    /**
     *
     */
    @ExcelProperty(value = "")
    private String groupId;

    /**
     * 邀请人
     */
    @ExcelProperty(value = "邀请人")
    private Long inviterId;

    /**
     * 被邀请人
     */
    @ExcelProperty(value = "被邀请人")
    private Long inviteeId;

    /**
     * 状态（0待接受 1已接受 2已拒绝）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=待接受,1=已接受,2=已拒绝")
    private Long inviteStatus;


}
