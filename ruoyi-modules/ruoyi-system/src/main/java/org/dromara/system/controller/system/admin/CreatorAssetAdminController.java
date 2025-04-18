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
import org.dromara.system.domain.vo.CreatorAssetVo;
import org.dromara.system.domain.bo.CreatorAssetBo;
import org.dromara.system.service.ICreatorAssetService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 创作者资产 管理端
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/creator/asset")
public class CreatorAssetAdminController extends BaseController {

    private final ICreatorAssetService creatorAssetService;

    /**
     * 查询创作者资产列表
     */
    @SaCheckPermission("system:asset:list")
    @GetMapping("/list")
    public TableDataInfo<CreatorAssetVo> list(CreatorAssetBo bo, PageQuery pageQuery) {
        return creatorAssetService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出创作者资产列表
     */
    @SaCheckPermission("system:asset:export")
    @Log(title = "创作者资产", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CreatorAssetBo bo, HttpServletResponse response) {
        List<CreatorAssetVo> list = creatorAssetService.queryList(bo);
        ExcelUtil.exportExcel(list, "创作者资产", CreatorAssetVo.class, response);
    }

    /**
     * 获取创作者资产详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("system:asset:query")
    @GetMapping("/{userId}")
    public R<CreatorAssetVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(creatorAssetService.queryById(userId));
    }

    /**
     * 新增创作者资产
     */
    @SaCheckPermission("system:asset:add")
    @Log(title = "创作者资产", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CreatorAssetBo bo) {
        return toAjax(creatorAssetService.insertByBo(bo));
    }

    /**
     * 修改创作者资产
     */
    @SaCheckPermission("system:asset:edit")
    @Log(title = "创作者资产", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CreatorAssetBo bo) {
        return toAjax(creatorAssetService.updateByBo(bo));
    }

    /**
     * 删除创作者资产
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("system:asset:remove")
    @Log(title = "创作者资产", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(creatorAssetService.deleteWithValidByIds(List.of(userIds), true));
    }
}
