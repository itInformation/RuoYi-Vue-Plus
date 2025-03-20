package org.dromara.circle.controller.admin;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.circle.domain.bo.CircleCategoryBo;
import org.dromara.circle.domain.vo.CircleCategoryVo;
import org.dromara.circle.service.ICircleCategoryService;
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
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 圈子分类
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/category")
public class CircleCategoryAdminController extends BaseController {

    private final ICircleCategoryService circleCategoryService;

    /**
     * 查询圈子分类列表
     */
    @SaCheckPermission("system:category:list")
    @GetMapping("/list")
    public TableDataInfo<CircleCategoryVo> list(CircleCategoryBo bo, PageQuery pageQuery) {
        return circleCategoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出圈子分类列表
     */
    @SaCheckPermission("system:category:export")
    @Log(title = "圈子分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CircleCategoryBo bo, HttpServletResponse response) {
        List<CircleCategoryVo> list = circleCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "圈子分类", CircleCategoryVo.class, response);
    }

    /**
     * 获取圈子分类详细信息
     *
     * @param catId 主键
     */
    @SaCheckPermission("system:category:query")
    @GetMapping("/{catId}")
    public R<CircleCategoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long catId) {
        return R.ok(circleCategoryService.queryById(catId));
    }

    /**
     * 新增圈子分类
     */
    @SaCheckPermission("system:category:add")
    @Log(title = "圈子分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleCategoryBo bo) {
        return toAjax(circleCategoryService.insertByBo(bo));
    }

    /**
     * 修改圈子分类
     */
    @SaCheckPermission("system:category:edit")
    @Log(title = "圈子分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleCategoryBo bo) {
        return toAjax(circleCategoryService.updateByBo(bo));
    }

    /**
     * 删除圈子分类
     *
     * @param catIds 主键串
     */
    @SaCheckPermission("system:category:remove")
    @Log(title = "圈子分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{catIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] catIds) {
        return toAjax(circleCategoryService.deleteWithValidByIds(List.of(catIds), true));
    }
}
