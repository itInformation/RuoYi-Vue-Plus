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
import org.dromara.system.domain.vo.UserAssetVo;
import org.dromara.system.domain.bo.UserAssetBo;
import org.dromara.system.service.IUserAssetService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 普通用户资产 管理端
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user/asset")
public class UserAssetAdminController extends BaseController {

    private final IUserAssetService userAssetService;

    /**
     * 查询普通用户资产列表
     */
    @SaCheckPermission("system:asset:list")
    @GetMapping("/list")
    public TableDataInfo<UserAssetVo> list(UserAssetBo bo, PageQuery pageQuery) {
        return userAssetService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出普通用户资产列表
     */
    @SaCheckPermission("system:asset:export")
    @Log(title = "普通用户资产", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(UserAssetBo bo, HttpServletResponse response) {
        List<UserAssetVo> list = userAssetService.queryList(bo);
        ExcelUtil.exportExcel(list, "普通用户资产", UserAssetVo.class, response);
    }

    /**
     * 获取普通用户资产详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("system:asset:query")
    @GetMapping("/{userId}")
    public R<UserAssetVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(userAssetService.queryById(userId));
    }

    /**
     * 新增普通用户资产
     */
    @SaCheckPermission("system:asset:add")
    @Log(title = "普通用户资产", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody UserAssetBo bo) {
        return toAjax(userAssetService.insertByBo(bo));
    }

    /**
     * 修改普通用户资产
     */
    @SaCheckPermission("system:asset:edit")
    @Log(title = "普通用户资产", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody UserAssetBo bo) {
        return toAjax(userAssetService.updateByBo(bo));
    }

    /**
     * 删除普通用户资产
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("system:asset:remove")
    @Log(title = "普通用户资产", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(userAssetService.deleteWithValidByIds(List.of(userIds), true));
    }
}
