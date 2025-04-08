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
import org.dromara.system.domain.vo.SysCreatorStatsVo;
import org.dromara.system.domain.bo.SysCreatorStatsBo;
import org.dromara.system.service.ISysCreatorStatsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 创作者统计
 *
 * @author Lion Li
 * @date 2025-04-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/creatorStats")
public class SysCreatorStatsController extends BaseController {

    private final ISysCreatorStatsService sysCreatorStatsService;

    /**
     * 查询创作者统计列表
     */
    @SaCheckPermission("system:creatorStats:list")
    @GetMapping("/list")
    public TableDataInfo<SysCreatorStatsVo> list(SysCreatorStatsBo bo, PageQuery pageQuery) {
        return sysCreatorStatsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出创作者统计列表
     */
    @SaCheckPermission("system:creatorStats:export")
    @Log(title = "创作者统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysCreatorStatsBo bo, HttpServletResponse response) {
        List<SysCreatorStatsVo> list = sysCreatorStatsService.queryList(bo);
        ExcelUtil.exportExcel(list, "创作者统计", SysCreatorStatsVo.class, response);
    }

    /**
     * 获取创作者统计详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("system:creatorStats:query")
    @GetMapping("/{userId}")
    public R<SysCreatorStatsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(sysCreatorStatsService.queryById(userId));
    }

    /**
     * 新增创作者统计
     */
    @SaCheckPermission("system:creatorStats:add")
    @Log(title = "创作者统计", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysCreatorStatsBo bo) {
        return toAjax(sysCreatorStatsService.insertByBo(bo));
    }

    /**
     * 修改创作者统计
     */
    @SaCheckPermission("system:creatorStats:edit")
    @Log(title = "创作者统计", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysCreatorStatsBo bo) {
        return toAjax(sysCreatorStatsService.updateByBo(bo));
    }

    /**
     * 删除创作者统计
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("system:creatorStats:remove")
    @Log(title = "创作者统计", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(sysCreatorStatsService.deleteWithValidByIds(List.of(userIds), true));
    }
}
