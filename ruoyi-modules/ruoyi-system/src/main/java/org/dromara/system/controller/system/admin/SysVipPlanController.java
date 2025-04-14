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
import org.dromara.system.domain.vo.SysVipPlanVo;
import org.dromara.system.domain.bo.SysVipPlanBo;
import org.dromara.system.service.ISysVipPlanService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 会员套餐
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/vipPlan")
public class SysVipPlanController extends BaseController {

    private final ISysVipPlanService sysVipPlanService;

    /**
     * 查询会员套餐列表
     */
    @SaCheckPermission("system:vipPlan:list")
    @GetMapping("/list")
    public TableDataInfo<SysVipPlanVo> list(SysVipPlanBo bo, PageQuery pageQuery) {
        return sysVipPlanService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员套餐列表
     */
    @SaCheckPermission("system:vipPlan:export")
    @Log(title = "会员套餐", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysVipPlanBo bo, HttpServletResponse response) {
        List<SysVipPlanVo> list = sysVipPlanService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员套餐", SysVipPlanVo.class, response);
    }

    /**
     * 获取会员套餐详细信息
     *
     * @param planId 主键
     */
    @SaCheckPermission("system:vipPlan:query")
    @GetMapping("/{planId}")
    public R<SysVipPlanVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long planId) {
        return R.ok(sysVipPlanService.queryById(planId));
    }

    /**
     * 新增会员套餐
     */
    @SaCheckPermission("system:vipPlan:add")
    @Log(title = "会员套餐", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysVipPlanBo bo) {
        return toAjax(sysVipPlanService.insertByBo(bo));
    }

    /**
     * 修改会员套餐
     */
    @SaCheckPermission("system:vipPlan:edit")
    @Log(title = "会员套餐", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysVipPlanBo bo) {
        return toAjax(sysVipPlanService.updateByBo(bo));
    }

    /**
     * 删除会员套餐
     *
     * @param planIds 主键串
     */
    @SaCheckPermission("system:vipPlan:remove")
    @Log(title = "会员套餐", businessType = BusinessType.DELETE)
    @DeleteMapping("/{planIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] planIds) {
        return toAjax(sysVipPlanService.deleteWithValidByIds(List.of(planIds), true));
    }
}
