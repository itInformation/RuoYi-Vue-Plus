package org.dromara.circle.controller.client;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.circle.domain.bo.CircleReviewLogBo;
import org.dromara.circle.domain.vo.CircleReviewLogVo;
import org.dromara.circle.service.ICircleReviewLogService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 圈子审核记录
 *
 * @author Lion Li
 * @date 2025-04-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/circle/reviewLog")
public class CircleReviewLogController extends BaseController {

    private final ICircleReviewLogService circleReviewLogService;

    /**
     * 查询圈子审核记录列表
     */
    @SaCheckPermission("circle:reviewLog:list")
    @GetMapping("/list")
    public TableDataInfo<CircleReviewLogVo> list(CircleReviewLogBo bo, PageQuery pageQuery) {
        return circleReviewLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出圈子审核记录列表
     */
    @SaCheckPermission("circle:reviewLog:export")
    @Log(title = "圈子审核记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CircleReviewLogBo bo, HttpServletResponse response) {
        List<CircleReviewLogVo> list = circleReviewLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "圈子审核记录", CircleReviewLogVo.class, response);
    }

    /**
     * 获取圈子审核记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("circle:reviewLog:query")
    @GetMapping("/{id}")
    public R<CircleReviewLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(circleReviewLogService.queryById(id));
    }

    /**
     * 新增圈子审核记录
     */
    @SaCheckPermission("circle:reviewLog:add")
    @Log(title = "圈子审核记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
//    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleReviewLogBo bo) {
        return toAjax(circleReviewLogService.insertByBo(bo));
    }

    /**
     * 修改圈子审核记录
     */
    @SaCheckPermission("circle:reviewLog:edit")
    @Log(title = "圈子审核记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
//    @PostMapping("/update")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleReviewLogBo bo) {
        return toAjax(circleReviewLogService.updateByBo(bo));
    }

    /**
     * 删除圈子审核记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("circle:reviewLog:remove")
    @Log(title = "圈子审核记录", businessType = BusinessType.DELETE)
//    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(circleReviewLogService.deleteWithValidByIds(List.of(ids), true));
    }


}
