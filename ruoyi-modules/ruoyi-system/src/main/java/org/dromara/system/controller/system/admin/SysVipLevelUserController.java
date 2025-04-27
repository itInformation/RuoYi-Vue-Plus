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
import org.dromara.system.domain.vo.SysVipLevelUserVo;
import org.dromara.system.domain.bo.SysVipLevelUserBo;
import org.dromara.system.service.ISysVipLevelUserService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户等级
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/vipLevelUser")
public class SysVipLevelUserController extends BaseController {

    private final ISysVipLevelUserService sysVipLevelUserService;

    /**
     * 查询用户等级列表
     */
    @SaCheckPermission("system:vipLevelUser:list")
    @GetMapping("/list")
    public TableDataInfo<SysVipLevelUserVo> list(SysVipLevelUserBo bo, PageQuery pageQuery) {
        return sysVipLevelUserService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户等级列表
     */
    @SaCheckPermission("system:vipLevelUser:export")
    @Log(title = "用户等级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysVipLevelUserBo bo, HttpServletResponse response) {
        List<SysVipLevelUserVo> list = sysVipLevelUserService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户等级", SysVipLevelUserVo.class, response);
    }

    /**
     * 获取用户等级详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:vipLevelUser:query")
    @GetMapping("/{id}")
    public R<SysVipLevelUserVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(sysVipLevelUserService.queryById(id));
    }

    /**
     * 新增用户等级
     */
    @SaCheckPermission("system:vipLevelUser:add")
    @Log(title = "用户等级", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysVipLevelUserBo bo) {
        return toAjax(sysVipLevelUserService.insertByBo(bo));
    }

    /**
     * 修改用户等级
     */
    @SaCheckPermission("system:vipLevelUser:edit")
    @Log(title = "用户等级", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysVipLevelUserBo bo) {
        return toAjax(sysVipLevelUserService.updateByBo(bo));
    }

    /**
     * 删除用户等级
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:vipLevelUser:remove")
    @Log(title = "用户等级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(sysVipLevelUserService.deleteWithValidByIds(List.of(ids), true));
    }
}
