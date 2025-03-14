package org.dromara.circle.domain.vo;

import java.util.Date;

import org.dromara.circle.domain.CircleMember;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 用户-圈子关系视图对象 circle_member
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleMember.class)
public class CircleMemberVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 关系记录ID
     */
    @ExcelProperty(value = "关系记录ID")
    private String memberId;

    /**
     * 圈子ID
     */
    @ExcelProperty(value = "圈子ID")
    private String groupId;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 角色类型（0普通成员 1管理员 2拥有者）
     */
    @ExcelProperty(value = "角色类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=普通成员,1=管理员,2=拥有者")
    private Long roleType;

    /**
     * 状态（0待审核 1正常 2已退出）
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=待审核,1=正常,2=已退出")
    private Long memberStatus;

    /**
     * 加入时间
     */
    @ExcelProperty(value = "加入时间")
    private Date joinTime;

    /**
     * 邀请人ID（用于邀请加入场景）
     */
    @ExcelProperty(value = "邀请人ID", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "用=于邀请加入场景")
    private Long inviteUser;

    /**
     * 最后活跃时间
     */
    @ExcelProperty(value = "最后活跃时间")
    private Date lastActive;


}
