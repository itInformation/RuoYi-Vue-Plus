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
import org.dromara.circle.domain.vo.CircleApplyVo;
import org.dromara.circle.domain.bo.CircleApplyBo;
import org.dromara.circle.service.ICircleApplyService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 圈子申请记录
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/apply")
public class CircleApplyController extends BaseController {

    private final ICircleApplyService circleApplyService;

    /**
     * 查询圈子申请记录列表
     */
    @SaCheckPermission("system:apply:list")
    @GetMapping("/list")
    public TableDataInfo<CircleApplyVo> list(CircleApplyBo bo, PageQuery pageQuery) {
        return circleApplyService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出圈子申请记录列表
     */
    @SaCheckPermission("system:apply:export")
    @Log(title = "圈子申请记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CircleApplyBo bo, HttpServletResponse response) {
        List<CircleApplyVo> list = circleApplyService.queryList(bo);
        ExcelUtil.exportExcel(list, "圈子申请记录", CircleApplyVo.class, response);
    }

    /**
     * 获取圈子申请记录详细信息
     *
     * @param applyId 主键
     */
    @SaCheckPermission("system:apply:query")
    @GetMapping("/{applyId}")
    public R<CircleApplyVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long applyId) {
        return R.ok(circleApplyService.queryById(applyId));
    }

    /**
     * 新增圈子申请记录
     */
    @SaCheckPermission("system:apply:add")
    @Log(title = "圈子申请记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleApplyBo bo) {
        return toAjax(circleApplyService.insertByBo(bo));
    }

    /**
     * 修改圈子申请记录
     */
    @SaCheckPermission("system:apply:edit")
    @Log(title = "圈子申请记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleApplyBo bo) {
        return toAjax(circleApplyService.updateByBo(bo));
    }

    /**
     * 删除圈子申请记录
     *
     * @param applyIds 主键串
     */
    @SaCheckPermission("system:apply:remove")
    @Log(title = "圈子申请记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{applyIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] applyIds) {
        return toAjax(circleApplyService.deleteWithValidByIds(List.of(applyIds), true));
    }
}
