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
import org.dromara.system.domain.vo.SysVipUserPointsVo;
import org.dromara.system.domain.bo.SysVipUserPointsBo;
import org.dromara.system.service.ISysVipUserPointsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分记录
 *
 * @author Lion Li
 * @date 2025-04-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/vipUserPoints")
public class SysVipUserPointsController extends BaseController {

    private final ISysVipUserPointsService sysVipUserPointsService;

    /**
     * 查询积分记录列表
     */
    @SaCheckPermission("system:vipUserPoints:list")
    @GetMapping("/list")
    public TableDataInfo<SysVipUserPointsVo> list(SysVipUserPointsBo bo, PageQuery pageQuery) {
        return sysVipUserPointsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出积分记录列表
     */
    @SaCheckPermission("system:vipUserPoints:export")
    @Log(title = "积分记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysVipUserPointsBo bo, HttpServletResponse response) {
        List<SysVipUserPointsVo> list = sysVipUserPointsService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分记录", SysVipUserPointsVo.class, response);
    }

    /**
     * 获取积分记录详细信息
     *
     * @param pointsId 主键
     */
    @SaCheckPermission("system:vipUserPoints:query")
    @GetMapping("/{pointsId}")
    public R<SysVipUserPointsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long pointsId) {
        return R.ok(sysVipUserPointsService.queryById(pointsId));
    }

    /**
     * 新增积分记录
     */
    @SaCheckPermission("system:vipUserPoints:add")
    @Log(title = "积分记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysVipUserPointsBo bo) {
        return toAjax(sysVipUserPointsService.insertByBo(bo));
    }

    /**
     * 修改积分记录
     */
    @SaCheckPermission("system:vipUserPoints:edit")
    @Log(title = "积分记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysVipUserPointsBo bo) {
        return toAjax(sysVipUserPointsService.updateByBo(bo));
    }

    /**
     * 删除积分记录
     *
     * @param pointsIds 主键串
     */
    @SaCheckPermission("system:vipUserPoints:remove")
    @Log(title = "积分记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pointsIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] pointsIds) {
        return toAjax(sysVipUserPointsService.deleteWithValidByIds(List.of(pointsIds), true));
    }
}
