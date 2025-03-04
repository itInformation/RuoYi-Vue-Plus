package org.dromara.system.domain.vo;

import org.dromara.system.domain.CircleContent;
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
 * 圈子内容视图对象 circle_content
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleContent.class)
public class CircleContentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 内容ID
     */
    @ExcelProperty(value = "内容ID")
    private Long contentId;

    /**
     * 所属圈子ID
     */
    @ExcelProperty(value = "所属圈子ID")
    private Long groupId;

    /**
     * 作者ID
     */
    @ExcelProperty(value = "作者ID")
    private Long userId;

    /**
     * 标题
     */
    @ExcelProperty(value = "标题")
    private String title;

    /**
     * 类型（1视频 2图文 3文字）
     */
    @ExcelProperty(value = "类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "1=视频,2=图文,3=文字")
    private String contentType;

    /**
     * 内容（JSON存储）
     */
    @ExcelProperty(value = "内容", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "J=SON存储")
    private String content;

    /**
     * 权限类型（0免费 1会员 2指定）
     */
    @ExcelProperty(value = "权限类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=免费,1=会员,2=指定")
    private String permType;


}
