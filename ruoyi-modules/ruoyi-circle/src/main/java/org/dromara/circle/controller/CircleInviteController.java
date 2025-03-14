package org.dromara.circle.controller;

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
import org.dromara.circle.domain.vo.CircleInviteVo;
import org.dromara.circle.domain.bo.CircleInviteBo;
import org.dromara.circle.service.ICircleInviteService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 邀请记录
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/invite")
public class CircleInviteController extends BaseController {

    private final ICircleInviteService circleInviteService;

    /**
     * 查询邀请记录列表
     */
    @SaCheckPermission("system:invite:list")
    @GetMapping("/list")
    public TableDataInfo<CircleInviteVo> list(CircleInviteBo bo, PageQuery pageQuery) {
        return circleInviteService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出邀请记录列表
     */
    @SaCheckPermission("system:invite:export")
    @Log(title = "邀请记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CircleInviteBo bo, HttpServletResponse response) {
        List<CircleInviteVo> list = circleInviteService.queryList(bo);
        ExcelUtil.exportExcel(list, "邀请记录", CircleInviteVo.class, response);
    }

    /**
     * 获取邀请记录详细信息
     *
     * @param inviteId 主键
     */
    @SaCheckPermission("system:invite:query")
    @GetMapping("/{inviteId}")
    public R<CircleInviteVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long inviteId) {
        return R.ok(circleInviteService.queryById(inviteId));
    }

    /**
     * 新增邀请记录
     */
    @SaCheckPermission("system:invite:add")
    @Log(title = "邀请记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleInviteBo bo) {
        return toAjax(circleInviteService.insertByBo(bo));
    }

    /**
     * 修改邀请记录
     */
    @SaCheckPermission("system:invite:edit")
    @Log(title = "邀请记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleInviteBo bo) {
        return toAjax(circleInviteService.updateByBo(bo));
    }

    /**
     * 删除邀请记录
     *
     * @param inviteIds 主键串
     */
    @SaCheckPermission("system:invite:remove")
    @Log(title = "邀请记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{inviteIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] inviteIds) {
        return toAjax(circleInviteService.deleteWithValidByIds(List.of(inviteIds), true));
    }
}
