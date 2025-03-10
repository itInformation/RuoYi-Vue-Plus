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
import org.dromara.system.domain.vo.PayConfigVo;
import org.dromara.system.domain.bo.PayConfigBo;
import org.dromara.system.service.IPayConfigService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 支付配置
 *
 * @author Lion Li
 * @date 2025-03-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/pay/config")
public class PayConfigController extends BaseController {

    private final IPayConfigService payConfigService;

    /**
     * 查询支付配置列表
     */
    @SaCheckPermission("system:config:list")
    @GetMapping("/list")
    public TableDataInfo<PayConfigVo> list(PayConfigBo bo, PageQuery pageQuery) {
        return payConfigService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出支付配置列表
     */
    @SaCheckPermission("system:config:export")
    @Log(title = "支付配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(PayConfigBo bo, HttpServletResponse response) {
        List<PayConfigVo> list = payConfigService.queryList(bo);
        ExcelUtil.exportExcel(list, "支付配置", PayConfigVo.class, response);
    }

    /**
     * 获取支付配置详细信息
     *
     * @param configId 主键
     */
    @SaCheckPermission("system:config:query")
    @GetMapping("/{configId}")
    public R<PayConfigVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long configId) {
        return R.ok(payConfigService.queryById(configId));
    }

    /**
     * 新增支付配置
     */
    @SaCheckPermission("system:config:add")
    @Log(title = "支付配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody PayConfigBo bo) {
        return toAjax(payConfigService.insertByBo(bo));
    }

    /**
     * 修改支付配置
     */
    @SaCheckPermission("system:config:edit")
    @Log(title = "支付配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PayConfigBo bo) {
        return toAjax(payConfigService.updateByBo(bo));
    }

    /**
     * 删除支付配置
     *
     * @param configIds 主键串
     */
    @SaCheckPermission("system:config:remove")
    @Log(title = "支付配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] configIds) {
        return toAjax(payConfigService.deleteWithValidByIds(List.of(configIds), true));
    }
}
