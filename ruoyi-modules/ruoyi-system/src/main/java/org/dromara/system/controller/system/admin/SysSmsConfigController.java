package org.dromara.system.controller.system.admin;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.sms.domain.bo.SysSmsConfigBo;
import org.dromara.common.sms.domain.vo.SysSmsConfigVo;
import org.dromara.common.sms.service.ISysSmsConfigService;
import org.dromara.common.web.core.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短信配置
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/smsConfig")
public class SysSmsConfigController extends BaseController {

    private final ISysSmsConfigService sysSmsConfigService;

    /**
     * 查询短信配置列表
     */
    @SaCheckPermission("system:smsConfig:list")
    @GetMapping("/list")
    public TableDataInfo<SysSmsConfigVo> list(SysSmsConfigBo bo, PageQuery pageQuery) {
        return sysSmsConfigService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出短信配置列表
     */
    @SaCheckPermission("system:smsConfig:export")
    @Log(title = "短信配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysSmsConfigBo bo, HttpServletResponse response) {
        List<SysSmsConfigVo> list = sysSmsConfigService.queryList(bo);
        ExcelUtil.exportExcel(list, "短信配置", SysSmsConfigVo.class, response);
    }

    /**
     * 获取短信配置详细信息
     *
     * @param configId 主键
     */
    @SaCheckPermission("system:smsConfig:query")
    @GetMapping("/{configId}")
    public R<SysSmsConfigVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long configId) {
        return R.ok(sysSmsConfigService.queryById(configId));
    }

    /**
     * 新增短信配置
     */
    @SaCheckPermission("system:smsConfig:add")
    @Log(title = "短信配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysSmsConfigBo bo) {
        return toAjax(sysSmsConfigService.insertByBo(bo));
    }

    /**
     * 修改短信配置
     */
    @SaCheckPermission("system:smsConfig:edit")
    @Log(title = "短信配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysSmsConfigBo bo) {
        return toAjax(sysSmsConfigService.updateByBo(bo));
    }

    /**
     * 删除短信配置
     *
     * @param configIds 主键串
     */
    @SaCheckPermission("system:smsConfig:remove")
    @Log(title = "短信配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] configIds) {
        return toAjax(sysSmsConfigService.deleteWithValidByIds(List.of(configIds), true));
    }
}
