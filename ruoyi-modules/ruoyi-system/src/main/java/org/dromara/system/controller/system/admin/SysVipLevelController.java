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
import org.dromara.system.domain.vo.SysVipLevelVo;
import org.dromara.system.domain.bo.SysVipLevelBo;
import org.dromara.system.service.ISysVipLevelService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 会员等级
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/vipLevel")
public class SysVipLevelController extends BaseController {

    private final ISysVipLevelService sysVipLevelService;

    /**
     * 查询会员等级列表
     */
    @SaCheckPermission("system:vipLevel:list")
    @GetMapping("/list")
    public TableDataInfo<SysVipLevelVo> list(SysVipLevelBo bo, PageQuery pageQuery) {
        return sysVipLevelService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员等级列表
     */
    @SaCheckPermission("system:vipLevel:export")
    @Log(title = "会员等级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysVipLevelBo bo, HttpServletResponse response) {
        List<SysVipLevelVo> list = sysVipLevelService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员等级", SysVipLevelVo.class, response);
    }

    /**
     * 获取会员等级详细信息
     *
     * @param levelId 主键
     */
    @SaCheckPermission("system:vipLevel:query")
    @GetMapping("/{levelId}")
    public R<SysVipLevelVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long levelId) {
        return R.ok(sysVipLevelService.queryById(levelId));
    }

    /**
     * 新增会员等级
     */
    @SaCheckPermission("system:vipLevel:add")
    @Log(title = "会员等级", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysVipLevelBo bo) {
        return toAjax(sysVipLevelService.insertByBo(bo));
    }

    /**
     * 修改会员等级
     */
    @SaCheckPermission("system:vipLevel:edit")
    @Log(title = "会员等级", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysVipLevelBo bo) {
        return toAjax(sysVipLevelService.updateByBo(bo));
    }

    /**
     * 删除会员等级
     *
     * @param levelIds 主键串
     */
    @SaCheckPermission("system:vipLevel:remove")
    @Log(title = "会员等级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{levelIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] levelIds) {
        return toAjax(sysVipLevelService.deleteWithValidByIds(List.of(levelIds), true));
    }
}
