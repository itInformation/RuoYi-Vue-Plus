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
import org.dromara.circle.domain.vo.CircleMemberVo;
import org.dromara.circle.domain.bo.CircleMemberBo;
import org.dromara.circle.service.ICircleMemberService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户-圈子关系
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/member")
public class CircleMemberController extends BaseController {

    private final ICircleMemberService circleMemberService;

    /**
     * 查询用户-圈子关系列表
     */
    @SaCheckPermission("system:member:list")
    @GetMapping("/list")
    public TableDataInfo<CircleMemberVo> list(CircleMemberBo bo, PageQuery pageQuery) {
        return circleMemberService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户-圈子关系列表
     */
    @SaCheckPermission("system:member:export")
    @Log(title = "用户-圈子关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CircleMemberBo bo, HttpServletResponse response) {
        List<CircleMemberVo> list = circleMemberService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户-圈子关系", CircleMemberVo.class, response);
    }

    /**
     * 获取用户-圈子关系详细信息
     *
     * @param memberId 主键
     */
    @SaCheckPermission("system:member:query")
    @GetMapping("/{memberId}")
    public R<CircleMemberVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long memberId) {
        return R.ok(circleMemberService.queryById(memberId));
    }

    /**
     * 新增用户-圈子关系
     */
    @SaCheckPermission("system:member:add")
    @Log(title = "用户-圈子关系", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleMemberBo bo) {
        return toAjax(circleMemberService.insertByBo(bo));
    }

    /**
     * 修改用户-圈子关系
     */
    @SaCheckPermission("system:member:edit")
    @Log(title = "用户-圈子关系", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleMemberBo bo) {
        return toAjax(circleMemberService.updateByBo(bo));
    }

    /**
     * 删除用户-圈子关系
     *
     * @param memberIds 主键串
     */
    @SaCheckPermission("system:member:remove")
    @Log(title = "用户-圈子关系", businessType = BusinessType.DELETE)
    @DeleteMapping("/{memberIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] memberIds) {
        return toAjax(circleMemberService.deleteWithValidByIds(List.of(memberIds), true));
    }
}
