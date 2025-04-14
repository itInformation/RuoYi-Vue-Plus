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
import org.dromara.system.domain.vo.SysUserVipVo;
import org.dromara.system.domain.bo.SysUserVipBo;
import org.dromara.system.service.ISysUserVipService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户会员信息
 *
 * @author Lion Li
 * @date 2025-04-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/userVip")
public class SysUserVipController extends BaseController {

    private final ISysUserVipService sysUserVipService;

    /**
     * 查询用户会员信息列表
     */
    @SaCheckPermission("system:userVip:list")
    @GetMapping("/list")
    public TableDataInfo<SysUserVipVo> list(SysUserVipBo bo, PageQuery pageQuery) {
        return sysUserVipService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户会员信息列表
     */
    @SaCheckPermission("system:userVip:export")
    @Log(title = "用户会员信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysUserVipBo bo, HttpServletResponse response) {
        List<SysUserVipVo> list = sysUserVipService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户会员信息", SysUserVipVo.class, response);
    }

    /**
     * 获取用户会员信息详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("system:userVip:query")
    @GetMapping("/{userId}")
    public R<SysUserVipVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(sysUserVipService.queryById(userId));
    }

    /**
     * 新增用户会员信息
     */
    @SaCheckPermission("system:userVip:add")
    @Log(title = "用户会员信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysUserVipBo bo) {
        return toAjax(sysUserVipService.insertByBo(bo));
    }

    /**
     * 修改用户会员信息
     */
    @SaCheckPermission("system:userVip:edit")
    @Log(title = "用户会员信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysUserVipBo bo) {
        return toAjax(sysUserVipService.updateByBo(bo));
    }

    /**
     * 删除用户会员信息
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("system:userVip:remove")
    @Log(title = "用户会员信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(sysUserVipService.deleteWithValidByIds(List.of(userIds), true));
    }
}
