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
import org.dromara.circle.domain.vo.UserCategoryWeightVo;
import org.dromara.circle.domain.bo.UserCategoryWeightBo;
import org.dromara.circle.service.IUserCategoryWeightService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户分类兴趣权重
 *
 * @author Lion Li
 * @date 2025-03-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/circle/categoryWeight")
public class UserCategoryWeightAdminController extends BaseController {

    private final IUserCategoryWeightService userCategoryWeightService;

    /**
     * 查询用户分类兴趣权重列表
     */
    @SaCheckPermission("circle:categoryWeight:list")
    @GetMapping("/list")
    public TableDataInfo<UserCategoryWeightVo> list(UserCategoryWeightBo bo, PageQuery pageQuery) {
        return userCategoryWeightService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户分类兴趣权重列表
     */
    @SaCheckPermission("circle:categoryWeight:export")
    @Log(title = "用户分类兴趣权重", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(UserCategoryWeightBo bo, HttpServletResponse response) {
        List<UserCategoryWeightVo> list = userCategoryWeightService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户分类兴趣权重", UserCategoryWeightVo.class, response);
    }

    /**
     * 获取用户分类兴趣权重详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("circle:categoryWeight:query")
    @GetMapping("/{userId}")
    public R<UserCategoryWeightVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(userCategoryWeightService.queryById(userId));
    }

    /**
     * 新增用户分类兴趣权重
     */
    @SaCheckPermission("circle:categoryWeight:add")
    @Log(title = "用户分类兴趣权重", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody UserCategoryWeightBo bo) {
        return toAjax(userCategoryWeightService.insertByBo(bo));
    }

    /**
     * 修改用户分类兴趣权重
     */
    @SaCheckPermission("circle:categoryWeight:edit")
    @Log(title = "用户分类兴趣权重", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody UserCategoryWeightBo bo) {
        return toAjax(userCategoryWeightService.updateByBo(bo));
    }

    /**
     * 删除用户分类兴趣权重
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("circle:categoryWeight:remove")
    @Log(title = "用户分类兴趣权重", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(userCategoryWeightService.deleteWithValidByIds(List.of(userIds), true));
    }
}
