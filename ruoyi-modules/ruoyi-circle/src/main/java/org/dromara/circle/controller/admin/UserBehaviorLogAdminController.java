package org.dromara.circle.controller.admin;

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
import org.dromara.circle.domain.vo.UserBehaviorLogVo;
import org.dromara.circle.domain.bo.UserBehaviorLogBo;
import org.dromara.circle.service.IUserBehaviorLogService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户行为日志
 *
 * @author Lion Li
 * @date 2025-03-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/circle/behaviorLog")
public class UserBehaviorLogAdminController extends BaseController {

    private final IUserBehaviorLogService userBehaviorLogService;

    /**
     * 查询用户行为日志列表
     */
    @SaCheckPermission("circle:behaviorLog:list")
    @GetMapping("/list")
    public TableDataInfo<UserBehaviorLogVo> list(UserBehaviorLogBo bo, PageQuery pageQuery) {
        return userBehaviorLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户行为日志列表
     */
    @SaCheckPermission("circle:behaviorLog:export")
    @Log(title = "用户行为日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(UserBehaviorLogBo bo, HttpServletResponse response) {
        List<UserBehaviorLogVo> list = userBehaviorLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户行为日志", UserBehaviorLogVo.class, response);
    }

    /**
     * 获取用户行为日志详细信息
     *
     * @param logId 主键
     */
    @SaCheckPermission("circle:behaviorLog:query")
    @GetMapping("/{logId}")
    public R<UserBehaviorLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long logId) {
        return R.ok(userBehaviorLogService.queryById(logId));
    }

    /**
     * 新增用户行为日志
     */
    @SaCheckPermission("circle:behaviorLog:add")
    @Log(title = "用户行为日志", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody UserBehaviorLogBo bo) {
        return toAjax(userBehaviorLogService.insertByBo(bo));
    }

    /**
     * 修改用户行为日志
     */
    @SaCheckPermission("circle:behaviorLog:edit")
    @Log(title = "用户行为日志", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody UserBehaviorLogBo bo) {
        return toAjax(userBehaviorLogService.updateByBo(bo));
    }

    /**
     * 删除用户行为日志
     *
     * @param logIds 主键串
     */
    @SaCheckPermission("circle:behaviorLog:remove")
    @Log(title = "用户行为日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] logIds) {
        return toAjax(userBehaviorLogService.deleteWithValidByIds(List.of(logIds), true));
    }
}
