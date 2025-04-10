package org.dromara.circle.controller.admin;

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
import org.dromara.circle.domain.vo.CircleGroupCategoryVo;
import org.dromara.circle.domain.bo.CircleGroupCategoryBo;
import org.dromara.circle.service.ICircleGroupCategoryService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 圈子-分类关系
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/groupCategory")
public class CircleGroupCategoryAdminController extends BaseController {

    private final ICircleGroupCategoryService circleGroupCategoryService;

    /**
     * 查询圈子-分类关系列表
     */
    @SaCheckPermission("system:groupCategory:list")
    @GetMapping("/list")
    public TableDataInfo<CircleGroupCategoryVo> list(CircleGroupCategoryBo bo, PageQuery pageQuery) {
        return circleGroupCategoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出圈子-分类关系列表
     */
    @SaCheckPermission("system:groupCategory:export")
    @Log(title = "圈子-分类关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CircleGroupCategoryBo bo, HttpServletResponse response) {
        List<CircleGroupCategoryVo> list = circleGroupCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "圈子-分类关系", CircleGroupCategoryVo.class, response);
    }

    /**
     * 获取圈子-分类关系详细信息
     *
     * @param groupId 主键
     */
    @SaCheckPermission("system:groupCategory:query")
    @GetMapping("/{groupId}")
    public R<CircleGroupCategoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String groupId) {
        return R.ok(circleGroupCategoryService.queryById(groupId));
    }

    /**
     * 新增圈子-分类关系
     */
    @SaCheckPermission("system:groupCategory:add")
    @Log(title = "圈子-分类关系", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleGroupCategoryBo bo) {
        return toAjax(circleGroupCategoryService.insertByBo(bo));
    }

    /**
     * 修改圈子-分类关系
     */
    @SaCheckPermission("system:groupCategory:edit")
    @Log(title = "圈子-分类关系", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleGroupCategoryBo bo) {
        return toAjax(circleGroupCategoryService.updateByBo(bo));
    }

    /**
     * 删除圈子-分类关系
     *
     * @param groupIds 主键串
     */
    @SaCheckPermission("system:groupCategory:remove")
    @Log(title = "圈子-分类关系", businessType = BusinessType.DELETE)
    @DeleteMapping("/{groupIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] groupIds) {
        return toAjax(circleGroupCategoryService.deleteWithValidByIds(List.of(groupIds), true));
    }
}
