package org.dromara.system.controller.system;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.system.domain.vo.CircleResourceVo;
import org.dromara.system.domain.bo.CircleResourceBo;
import org.dromara.system.service.ICircleResourceService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 资源文件
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/resource")
public class CircleResourceController extends BaseController {

    private final ICircleResourceService circleResourceService;

    /**
     * 查询资源文件列表
     */
    @SaCheckPermission("system:resource:list")
    @GetMapping("/list")
    public TableDataInfo<CircleResourceVo> list(CircleResourceBo bo, PageQuery pageQuery) {
        return circleResourceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出资源文件列表
     */
    @SaCheckPermission("system:resource:export")
    @Log(title = "资源文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CircleResourceBo bo, HttpServletResponse response) {
        List<CircleResourceVo> list = circleResourceService.queryList(bo);
        ExcelUtil.exportExcel(list, "资源文件", CircleResourceVo.class, response);
    }

    /**
     * 获取资源文件详细信息
     *
     * @param resourceId 主键
     */
    @SaCheckPermission("system:resource:query")
    @GetMapping("/{resourceId}")
    public R<CircleResourceVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String resourceId) {
        return R.ok(circleResourceService.queryById(resourceId));
    }

    /**
     * 新增资源文件
     */
    @SaCheckPermission("system:resource:add")
    @Log(title = "资源文件", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleResourceBo bo) {
        return toAjax(circleResourceService.insertByBo(bo));
    }

    /**
     * 修改资源文件
     */
    @SaCheckPermission("system:resource:edit")
    @Log(title = "资源文件", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleResourceBo bo) {
        return toAjax(circleResourceService.updateByBo(bo));
    }

    /**
     * 删除资源文件
     *
     * @param resourceIds 主键串
     */
    @SaCheckPermission("system:resource:remove")
    @Log(title = "资源文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{resourceIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] resourceIds) {
        return toAjax(circleResourceService.deleteWithValidByIds(List.of(resourceIds), true));
    }
}
