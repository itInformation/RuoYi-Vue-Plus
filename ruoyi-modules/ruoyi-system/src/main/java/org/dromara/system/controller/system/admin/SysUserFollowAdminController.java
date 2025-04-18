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
import org.dromara.system.domain.vo.SysUserFollowVo;
import org.dromara.system.domain.bo.SysUserFollowBo;
import org.dromara.system.service.ISysUserFollowService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户关注关系
 *
 * @author Lion Li
 * @date 2025-04-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/userFollow")
public class SysUserFollowAdminController extends BaseController {

    private final ISysUserFollowService sysUserFollowService;

    /**
     * 查询用户关注关系列表
     */
    @SaCheckPermission("system:userFollow:list")
    @GetMapping("/list")
    public TableDataInfo<SysUserFollowVo> list(SysUserFollowBo bo, PageQuery pageQuery) {
        return sysUserFollowService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户关注关系列表
     */
    @SaCheckPermission("system:userFollow:export")
    @Log(title = "用户关注关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysUserFollowBo bo, HttpServletResponse response) {
        List<SysUserFollowVo> list = sysUserFollowService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户关注关系", SysUserFollowVo.class, response);
    }

    /**
     * 获取用户关注关系详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:userFollow:query")
    @GetMapping("/{id}")
    public R<SysUserFollowVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(sysUserFollowService.queryById(id));
    }

    /**
     * 新增用户关注关系
     */
    @SaCheckPermission("system:userFollow:add")
    @Log(title = "用户关注关系", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysUserFollowBo bo) {
        return toAjax(sysUserFollowService.insertByBo(bo));
    }

    /**
     * 修改用户关注关系
     */
    @SaCheckPermission("system:userFollow:edit")
    @Log(title = "用户关注关系", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysUserFollowBo bo) {
        return toAjax(sysUserFollowService.updateByBo(bo));
    }

    /**
     * 删除用户关注关系
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:userFollow:remove")
    @Log(title = "用户关注关系", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(sysUserFollowService.deleteWithValidByIds(List.of(ids), true));
    }
}
