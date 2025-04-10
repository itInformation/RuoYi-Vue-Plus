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
import org.dromara.common.web.core.BaseController;
import org.dromara.system.domain.bo.ApplyMainBo;
import org.dromara.system.domain.bo.ApplyMainReviewBo;
import org.dromara.system.domain.vo.ApplyMainVo;
import org.dromara.system.service.IApplyMainService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 入驻申请管理端使用
 *
 * @author Lion Li
 * @date 2025-03-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/platform/apply")
public class ApplyMainAdminController extends BaseController {

    private final IApplyMainService applyMainService;

    /**
     * 查询入驻申请列表
     */
    @SaCheckPermission("system:main:list")
    @GetMapping("/list")
    public TableDataInfo<ApplyMainVo> list(ApplyMainBo bo, PageQuery pageQuery) {
        return applyMainService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出入驻申请列表
     */
    @SaCheckPermission("system:main:export")
    @Log(title = "入驻申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ApplyMainBo bo, HttpServletResponse response) {
        List<ApplyMainVo> list = applyMainService.queryList(bo);
        ExcelUtil.exportExcel(list, "入驻申请", ApplyMainVo.class, response);
    }

    /**
     * 获取入驻申请详细信息
     *
     * @param applyId 主键
     */
    @SaCheckPermission("system:main:query")
    @GetMapping("/{applyId}")
    public R<ApplyMainVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long applyId) {
        return R.ok(applyMainService.queryById(applyId));
    }

    /**
     * 新增入驻申请
     */
    @SaCheckPermission("system:main:add")
    @Log(title = "入驻申请", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ApplyMainBo bo) {
        return toAjax(applyMainService.insertByBo(bo));
    }

    /**
     * 修改入驻申请
     */
    @SaCheckPermission("system:main:review")
    @Log(title = "入驻申请审核", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/review")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ApplyMainReviewBo bo) {
        return toAjax(applyMainService.reviewApplyMain(bo));
    }

    /**
     * 修改入驻申请
     */
    @SaCheckPermission("system:main:edit")
    @Log(title = "修改入驻申请", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping(value = "/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ApplyMainBo bo) {
        return toAjax(applyMainService.updateByBo(bo));
    }

    /**
     * 删除入驻申请
     *
     * @param applyIds 主键串
     */
    @SaCheckPermission("system:main:remove")
    @Log(title = "入驻申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{applyIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] applyIds) {
        return toAjax(applyMainService.deleteWithValidByIds(List.of(applyIds), true));
    }
}
