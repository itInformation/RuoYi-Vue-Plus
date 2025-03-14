package org.dromara.circle.domain.vo;

import org.dromara.circle.domain.CircleResource;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 资源文件视图对象 circle_resource
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CircleResource.class)
public class CircleResourceVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    @ExcelProperty(value = "资源ID")
    private String resourceId;

    /**
     * 关联内容ID
     */
    @ExcelProperty(value = "关联内容ID")
    private String contentId;

    /**
     * 文件类型
     */
    @ExcelProperty(value = "文件类型")
    private String fileType;

    /**
     * 存储路径
     */
    @ExcelProperty(value = "存储路径")
    private String filePath;


}
