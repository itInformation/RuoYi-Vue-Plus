package org.dromara.system.controller.system.admin;

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
import org.dromara.system.domain.vo.SysAppVersionVo;
import org.dromara.system.domain.bo.SysAppVersionBo;
import org.dromara.system.service.ISysAppVersionService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * App版本信息
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/appVersion")
public class SysAppVersionAdminController extends BaseController {

    private final ISysAppVersionService sysAppVersionService;

    /**
     * 查询App版本信息列表
     */
    @SaCheckPermission("system:appVersion:list")
    @GetMapping("/list")
    public TableDataInfo<SysAppVersionVo> list(SysAppVersionBo bo, PageQuery pageQuery) {
        return sysAppVersionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出App版本信息列表
     */
    @SaCheckPermission("system:appVersion:export")
    @Log(title = "App版本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysAppVersionBo bo, HttpServletResponse response) {
        List<SysAppVersionVo> list = sysAppVersionService.queryList(bo);
        ExcelUtil.exportExcel(list, "App版本信息", SysAppVersionVo.class, response);
    }

    /**
     * 获取App版本信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:appVersion:query")
    @GetMapping("/{id}")
    public R<SysAppVersionVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(sysAppVersionService.queryById(id));
    }

    /**
     * 新增App版本信息
     */
    @SaCheckPermission("system:appVersion:add")
    @Log(title = "App版本信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysAppVersionBo bo) {
        return toAjax(sysAppVersionService.insertByBo(bo));
    }

    /**
     * 修改App版本信息
     */
    @SaCheckPermission("system:appVersion:edit")
    @Log(title = "App版本信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysAppVersionBo bo) {
        return toAjax(sysAppVersionService.updateByBo(bo));
    }

    /**
     * 删除App版本信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:appVersion:remove")
    @Log(title = "App版本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(sysAppVersionService.deleteWithValidByIds(List.of(ids), true));
    }
}
