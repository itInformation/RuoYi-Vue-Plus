package org.dromara.system.controller.system.client;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.system.domain.bo.UserDiamondLogBo;
import org.dromara.system.domain.vo.UserDiamondLogVo;
import org.dromara.system.service.IUserDiamondLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户钻石流水 app端
 *
 * @author Lion Li
 * @date 2025-04-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/client/diamondLog")
public class UserDiamondLogClientController extends BaseController {

    private final IUserDiamondLogService userDiamondLogService;

    /**
     * 查询用户钻石流水列表
     */
    @SaCheckPermission("client:diamondLog:list")
    @GetMapping("/list")
    public TableDataInfo<UserDiamondLogVo> list(UserDiamondLogBo bo, PageQuery pageQuery) {
        return userDiamondLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户钻石流水列表
     */
    @SaCheckPermission("client:diamondLog:export")
    @Log(title = "用户钻石流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(UserDiamondLogBo bo, HttpServletResponse response) {
        List<UserDiamondLogVo> list = userDiamondLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户钻石流水", UserDiamondLogVo.class, response);
    }

    /**
     * 获取用户钻石流水详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("client:diamondLog:query")
    @GetMapping("/{id}")
    public R<UserDiamondLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(userDiamondLogService.queryById(id));
    }

    /**
     * 新增用户钻石流水
     */
    @SaCheckPermission("client:diamondLog:add")
    @Log(title = "用户钻石流水", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody UserDiamondLogBo bo) {
        return toAjax(userDiamondLogService.insertByBo(bo));
    }

    /**
     * 修改用户钻石流水
     */
    @SaCheckPermission("client:diamondLog:edit")
    @Log(title = "用户钻石流水", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody UserDiamondLogBo bo) {
        return toAjax(userDiamondLogService.updateByBo(bo));
    }

    /**
     * 删除用户钻石流水
     *
     * @param ids 主键串
     */
    @SaCheckPermission("client:diamondLog:remove")
    @Log(title = "用户钻石流水", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(userDiamondLogService.deleteWithValidByIds(List.of(ids), true));
    }
}
