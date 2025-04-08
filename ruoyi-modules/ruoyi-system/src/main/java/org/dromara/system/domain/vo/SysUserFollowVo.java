package org.dromara.system.domain.vo;

import org.dromara.system.domain.SysUserFollow;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 用户关注关系视图对象 sys_user_follow
 *
 * @author Lion Li
 * @date 2025-04-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUserFollow.class)
public class SysUserFollowVo implements Serializable {

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
    @ExcelProperty(value = "关注者ID")
    private Long userId;

    /**
     * 被关注创作者ID
     */
    @ExcelProperty(value = "被关注创作者ID")
    private Long creatorId;


}
