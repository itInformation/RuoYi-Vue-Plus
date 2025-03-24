package org.dromara.circle.controller.admin;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.circle.domain.bo.CircleGroupStatusBo;
import org.springframework.beans.BeanUtils;
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
import org.dromara.circle.domain.vo.CircleGroupVo;
import org.dromara.circle.domain.bo.CircleGroupBo;
import org.dromara.circle.service.ICircleGroupService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 圈子主体
 *
 * @author Lion Li
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/group")
public class CircleGroupAdminController extends BaseController {

    private final ICircleGroupService circleGroupService;

    /**
     * 查询圈子主体列表
     */
    @SaCheckPermission("system:group:list")
    @GetMapping("/list")
    public TableDataInfo<CircleGroupVo> list(CircleGroupBo bo, PageQuery pageQuery) {
        return circleGroupService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询需要审核的圈子
     */
    @SaCheckPermission("system:group:list")
    @GetMapping("/getReviewList")
    public TableDataInfo<CircleGroupVo> getReviewList(CircleGroupBo bo, PageQuery pageQuery) {
        return circleGroupService.queryReviewPageList(bo, pageQuery);
    }

    /**
     * 查询回收站圈子主体列表
     */
    @SaCheckPermission("system:group:list")
    @GetMapping("/listWithRecycleBin")
    public TableDataInfo<CircleGroupVo> listWithRecycleBin(CircleGroupBo bo, PageQuery pageQuery) {
        return circleGroupService.queryPageListWithRecycleBin(bo, pageQuery);
    }

    /**
     * 导出圈子主体列表
     */
    @SaCheckPermission("system:group:export")
    @Log(title = "圈子主体", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CircleGroupBo bo, HttpServletResponse response) {
        List<CircleGroupVo> list = circleGroupService.queryList(bo);
        ExcelUtil.exportExcel(list, "圈子主体", CircleGroupVo.class, response);
    }

    /**
     * 获取圈子主体详细信息
     *
     * @param groupId 主键
     */
    @SaCheckPermission("system:group:query")
    @GetMapping("/{groupId}")
    public R<CircleGroupVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long groupId) {
        return R.ok(circleGroupService.queryById(groupId));
    }
    /**
     * 获取回收站圈子主体详细信息
     *
     * @param groupId 主键
     */
    @SaCheckPermission("system:group:query")
    @GetMapping("/getInfoWithRecycleBin/{groupId}")
    public R<CircleGroupVo> getInfoWithRecycleBin(@NotNull(message = "主键不能为空")
                                    @PathVariable Long groupId) {
        return R.ok(circleGroupService.queryByIdWithRecycleBin(groupId));
    }
    /**
     * 新增圈子主体
     */
    @SaCheckPermission("system:group:add")
    @Log(title = "圈子主体", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CircleGroupBo bo) {
        return toAjax(circleGroupService.insertByBo(bo));
    }

    /**
     * 修改圈子主体
     */
    @SaCheckPermission("system:group:edit")
    @Log(title = "圈子主体", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CircleGroupBo bo) {
        return toAjax(circleGroupService.updateByBo(bo));
    }

    /**
     * 修改圈子状态，启用 0 ，禁用 1
     */
    @SaCheckPermission("system:group:edit")
    @Log(title = "圈子主体", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/updateStatus")
    public R<Void> updateStatus(@Validated(EditGroup.class) @RequestBody CircleGroupStatusBo bo) {
        CircleGroupBo groupBo = new CircleGroupBo();
        BeanUtils.copyProperties(bo, groupBo);
        return toAjax(circleGroupService.updateByBo(groupBo));
    }

    /**
     * 删除圈子主体
     *
     * @param groupIds 主键串
     */
    @SaCheckPermission("system:group:remove")
    @Log(title = "圈子主体", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteByIds/{groupIds}")
    public R<Void> deleteByIds(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] groupIds) {
        return toAjax(circleGroupService.deleteWithValidByIds(List.of(groupIds), true));
    }


    /**
     * 删除回收站中的圈子主体
     *
     * @param groupIds 主键串
     */
    @SaCheckPermission("system:group:remove")
    @Log(title = "圈子主体", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteRecycleBinByIds/{groupIds}")
    public R<Void> deleteRecycleBinByIds(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] groupIds) {
        return toAjax(circleGroupService.deleteRecycleBinByIds(List.of(groupIds), true));
    }


    /**
     * 删除圈子主体
     *
     */
    @SaCheckPermission("system:group:remove")
    @Log(title = "圈子主体", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteById/{groupId}")
    public R<Void> deleteById(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long groupId) {
        return toAjax(circleGroupService.deleteWithValidById(groupId, true));
    }

    /**
     * 删除圈子主体
     *
     */
    @SaCheckPermission("system:group:remove")
    @Log(title = "圈子主体", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteRecycleBinById/{groupId}")
    public R<Void> deleteRecycleBinById(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long groupId) {
        return toAjax(circleGroupService.deleteRecycleBinById(groupId, true));
    }
}
