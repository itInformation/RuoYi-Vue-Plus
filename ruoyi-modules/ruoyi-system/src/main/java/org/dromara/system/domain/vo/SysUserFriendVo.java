package org.dromara.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.system.domain.SysUserFollow;
import org.dromara.system.domain.SysUserFriend;

import java.io.Serial;
import java.io.Serializable;

/**
 * description:
 *
 * @author minghuiZhang
 * @date created in 下午10:37 2025/4/8
 * modified by
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUserFriend.class)
public class SysUserFriendVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 关注者ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 被关注创作者ID
     */
    @ExcelProperty(value = "朋友ID")
    private Long friendId;
}
