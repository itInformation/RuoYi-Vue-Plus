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
import org.dromara.circle.domain.vo.CircleAuditLogVo;
import org.dromara.circle.domain.bo.CircleAuditLogBo;
import org.dromara.circle.service.ICircleAuditLogService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 【请填写功能名称】
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/auditLog")
public class CircleAuditLogAdminController extends BaseController {

    private final ICircleAuditLogService circleAuditLogService;

    /**
     * 查询【请填写功能名称】列表
     */
    @SaCheckPermission("system:auditLog:list")
    @GetMapping("/list")
    public TableDataInfo<CircleAuditLogVo> list(CircleAuditLogBo bo, PageQuery pageQuery) {
        return circleAuditLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @SaCheckPermission("system:auditLog:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CircleAuditLogBo bo, HttpServletResponse response) {
        List<CircleAuditLogVo> list = circleAuditLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "【请填写功能名称】", CircleAuditLogVo.class, response);
    }

    /**
     * 获取【请填写功能名称】详细信息
     *
     * @param logId 主键
     */
    @SaCheckPermission("system:auditLog:query")
    @GetMapping("/{logId}")
    public R<CircleAuditLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long logId) {
        return R.ok(circleAuditLogService.queryById(logId));
    }

    /**
     * 新增【请填写功能名称】
     */
    @SaCheckPermission("system:auditLog:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleAuditLogBo bo) {
        return toAjax(circleAuditLogService.insertByBo(bo));
    }

    /**
     * 修改【请填写功能名称】
     */
    @SaCheckPermission("system:auditLog:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleAuditLogBo bo) {
        return toAjax(circleAuditLogService.updateByBo(bo));
    }

    /**
     * 删除【请填写功能名称】
     *
     * @param logIds 主键串
     */
    @SaCheckPermission("system:auditLog:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] logIds) {
        return toAjax(circleAuditLogService.deleteWithValidByIds(List.of(logIds), true));
    }
}
