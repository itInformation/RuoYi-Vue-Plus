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
import org.dromara.circle.domain.vo.CircleContentVo;
import org.dromara.circle.domain.bo.CircleContentBo;
import org.dromara.circle.service.ICircleContentService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 圈子内容
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/content")
public class CircleContentAdminController extends BaseController {

    private final ICircleContentService circleContentService;

    /**
     * 查询圈子内容列表
     */
    @SaCheckPermission("system:content:list")
    @GetMapping("/list")
    public TableDataInfo<CircleContentVo> list(CircleContentBo bo, PageQuery pageQuery) {
        return circleContentService.queryPageList(bo, pageQuery);
    }


    /**
     * 查询圈子内容审核失败列表
     */
    @SaCheckPermission("system:content:failureList")
    @GetMapping("/failureList")
    public TableDataInfo<CircleContentVo> failureList(CircleContentBo bo, PageQuery pageQuery) {
        return circleContentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出圈子内容列表
     */
    @SaCheckPermission("system:content:export")
    @Log(title = "圈子内容", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CircleContentBo bo, HttpServletResponse response) {
        List<CircleContentVo> list = circleContentService.queryList(bo);
        ExcelUtil.exportExcel(list, "圈子内容", CircleContentVo.class, response);
    }

    /**
     * 获取圈子内容详细信息
     *
     * @param contentId 主键
     */
    @SaCheckPermission("system:content:query")
    @GetMapping("/{contentId}")
    public R<CircleContentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long contentId) {
        return R.ok(circleContentService.queryById(contentId));
    }

    /**
     * 新增圈子内容
     */
    @SaCheckPermission("system:content:add")
    @Log(title = "圈子内容", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleContentBo bo) {
        return toAjax(circleContentService.insertByBo(bo));
    }

    /**
     * 修改圈子内容
     */
    @SaCheckPermission("system:content:edit")
    @Log(title = "圈子内容", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleContentBo bo) {
        return toAjax(circleContentService.updateByBo(bo));
    }

    /**
     * 删除圈子内容
     *
     * @param contentIds 主键串
     */
    @SaCheckPermission("system:content:remove")
    @Log(title = "圈子内容", businessType = BusinessType.DELETE)
    @DeleteMapping("/{contentIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] contentIds) {
        return toAjax(circleContentService.deleteWithValidByIds(List.of(contentIds), true));
    }
}
