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
import org.dromara.system.domain.vo.CreatorIncomeLogVo;
import org.dromara.system.domain.bo.CreatorIncomeLogBo;
import org.dromara.system.service.ICreatorIncomeLogService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 创作者收入明细 管理端
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/incomeLog")
public class CreatorIncomeLogAdminController extends BaseController {

    private final ICreatorIncomeLogService creatorIncomeLogService;

    /**
     * 查询创作者收入明细列表
     */
    @SaCheckPermission("system:incomeLog:list")
    @GetMapping("/list")
    public TableDataInfo<CreatorIncomeLogVo> list(CreatorIncomeLogBo bo, PageQuery pageQuery) {
        return creatorIncomeLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出创作者收入明细列表
     */
    @SaCheckPermission("system:incomeLog:export")
    @Log(title = "创作者收入明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CreatorIncomeLogBo bo, HttpServletResponse response) {
        List<CreatorIncomeLogVo> list = creatorIncomeLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "创作者收入明细", CreatorIncomeLogVo.class, response);
    }

    /**
     * 获取创作者收入明细详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:incomeLog:query")
    @GetMapping("/{id}")
    public R<CreatorIncomeLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(creatorIncomeLogService.queryById(id));
    }

    /**
     * 新增创作者收入明细
     */
    @SaCheckPermission("system:incomeLog:add")
    @Log(title = "创作者收入明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CreatorIncomeLogBo bo) {
        return toAjax(creatorIncomeLogService.insertByBo(bo));
    }

    /**
     * 修改创作者收入明细
     */
    @SaCheckPermission("system:incomeLog:edit")
    @Log(title = "创作者收入明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CreatorIncomeLogBo bo) {
        return toAjax(creatorIncomeLogService.updateByBo(bo));
    }

    /**
     * 删除创作者收入明细
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:incomeLog:remove")
    @Log(title = "创作者收入明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(creatorIncomeLogService.deleteWithValidByIds(List.of(ids), true));
    }
}
