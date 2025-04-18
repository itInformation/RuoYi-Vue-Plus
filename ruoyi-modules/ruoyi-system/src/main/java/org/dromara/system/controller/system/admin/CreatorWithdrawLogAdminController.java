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
import org.dromara.system.domain.vo.CreatorWithdrawLogVo;
import org.dromara.system.domain.bo.CreatorWithdrawLogBo;
import org.dromara.system.service.ICreatorWithdrawLogService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 创作者提现记录 管理端
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/withdrawLog")
public class CreatorWithdrawLogAdminController extends BaseController {

    private final ICreatorWithdrawLogService creatorWithdrawLogService;

    /**
     * 查询创作者提现记录列表
     */
    @SaCheckPermission("system:withdrawLog:list")
    @GetMapping("/list")
    public TableDataInfo<CreatorWithdrawLogVo> list(CreatorWithdrawLogBo bo, PageQuery pageQuery) {
        return creatorWithdrawLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出创作者提现记录列表
     */
    @SaCheckPermission("system:withdrawLog:export")
    @Log(title = "创作者提现记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CreatorWithdrawLogBo bo, HttpServletResponse response) {
        List<CreatorWithdrawLogVo> list = creatorWithdrawLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "创作者提现记录", CreatorWithdrawLogVo.class, response);
    }

    /**
     * 获取创作者提现记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:withdrawLog:query")
    @GetMapping("/{id}")
    public R<CreatorWithdrawLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(creatorWithdrawLogService.queryById(id));
    }

    /**
     * 新增创作者提现记录
     */
    @SaCheckPermission("system:withdrawLog:add")
    @Log(title = "创作者提现记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CreatorWithdrawLogBo bo) {
        return toAjax(creatorWithdrawLogService.insertByBo(bo));
    }

    /**
     * 修改创作者提现记录
     */
    @SaCheckPermission("system:withdrawLog:edit")
    @Log(title = "创作者提现记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CreatorWithdrawLogBo bo) {
        return toAjax(creatorWithdrawLogService.updateByBo(bo));
    }

    /**
     * 删除创作者提现记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:withdrawLog:remove")
    @Log(title = "创作者提现记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(creatorWithdrawLogService.deleteWithValidByIds(List.of(ids), true));
    }
}
