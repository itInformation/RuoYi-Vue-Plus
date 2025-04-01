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
import org.dromara.system.domain.vo.SysAuthRecordVo;
import org.dromara.system.domain.bo.SysAuthRecordBo;
import org.dromara.system.service.ISysAuthRecordService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 【请填写功能名称】
 *
 * @author Lion Li
 * @date 2025-04-01
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/authRecord")
public class SysAuthRecordController extends BaseController {

    private final ISysAuthRecordService sysAuthRecordService;

    /**
     * 查询【请填写功能名称】列表
     */
    @SaCheckPermission("system:authRecord:list")
    @GetMapping("/list")
    public TableDataInfo<SysAuthRecordVo> list(SysAuthRecordBo bo, PageQuery pageQuery) {
        return sysAuthRecordService.queryPageList(bo, pageQuery);
    }


    /**
     * 导出【请填写功能名称】列表
     */
    @SaCheckPermission("system:authRecord:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysAuthRecordBo bo, HttpServletResponse response) {
        List<SysAuthRecordVo> list = sysAuthRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "【请填写功能名称】", SysAuthRecordVo.class, response);
    }

    /**
     * 获取【请填写功能名称】详细信息
     *
     * @param recordId 主键
     */
    @SaCheckPermission("system:authRecord:query")
    @GetMapping("/{recordId}")
    public R<SysAuthRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long recordId) {
        return R.ok(sysAuthRecordService.queryById(recordId));
    }

    /**
     * 新增【请填写功能名称】
     */
    @SaCheckPermission("system:authRecord:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysAuthRecordBo bo) {
        return toAjax(sysAuthRecordService.insertByBo(bo));
    }

    /**
     * 修改【请填写功能名称】
     */
    @SaCheckPermission("system:authRecord:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysAuthRecordBo bo) {
        return toAjax(sysAuthRecordService.updateByBo(bo));
    }

    /**
     * 删除【请填写功能名称】
     *
     * @param recordIds 主键串
     */
    @SaCheckPermission("system:authRecord:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] recordIds) {
        return toAjax(sysAuthRecordService.deleteWithValidByIds(List.of(recordIds), true));
    }
}
